/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.core.presentation.BiometricAuthManager
import com.sazim.teebay.core.utils.toIso8601String
import com.sazim.teebay.products.domain.model.ProductRentRequest
import com.sazim.teebay.products.domain.usecase.AddProductUseCase
import com.sazim.teebay.products.domain.usecase.DeleteProductUseCase
import com.sazim.teebay.products.domain.usecase.GetAllProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetCategoriesUseCase
import com.sazim.teebay.products.domain.usecase.GetMyProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetProductUseCase
import com.sazim.teebay.products.domain.usecase.UpdateProductUseCase
import com.sazim.teebay.products.domain.usecase.BuyProductUseCase
import com.sazim.teebay.products.domain.usecase.ProductRentUseCase
import com.sazim.teebay.products.domain.utils.RentOption
import com.sazim.teebay.products.presentation.ProductsEvents.*
import kotlinx.coroutines.channels.Channel
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val sessionManager: SessionManager,
    private val biometricManager: BiometricAuthManager,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val getMyProductsUseCase: GetMyProductsUseCase,
    private val categoryUseCase: GetCategoriesUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val buyProductUseCase: BuyProductUseCase,
    private val productRentUseCase: ProductRentUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(ProductsState(isBiometricEnabled = sessionManager.isBiometricLoginEnabled()))
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ProductsEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onAction(action: UserAction) {
        when (action) {
            UserAction.Logout -> logout()
            UserAction.ToggleBiometric -> toggleBiometricLogin()
            is UserAction.ProductTitleTyped -> {
                _state.update {
                    it.copy(productTitle = action.title)
                }
            }

            UserAction.NextPressedFromTitleScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(NavigateToCategorySelectScreen)
                }
            }

            is UserAction.CategoriesSelected -> {
                _state.update {
                    it.copy(selectedCategories = action.selectedCategories)
                }
            }

            UserAction.OnBackPressed -> {
                viewModelScope.launch {
                    _uiEvent.send(PopBackStack)
                }
            }

            UserAction.NextPressedFromCategoryScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(NavigateToDescScreen)
                }
            }

            UserAction.NextPressedFromSummaryScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(NavigateToProductPicScreen)
                }
            }

            is UserAction.ProductSummaryTyped -> {
                _state.update {
                    it.copy(productDesc = action.title)
                }
            }

            UserAction.NextPressedFromImgUpload -> {
                viewModelScope.launch {
                    _uiEvent.send(NavigateToProductPriceScreen)
                }
            }

            is UserAction.PurchasePriceTyped -> {
                _state.update {
                    it.copy(purchasePrice = action.title)
                }
            }

            is UserAction.RentPriceTyped -> {
                _state.update {
                    it.copy(rentPrice = action.title)
                }
            }

            is UserAction.RentOptionSelected -> {
                _state.update {
                    it.copy(selectedRentalOption = action.option)
                }
            }

            UserAction.NextPressedFromPriceScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(NavigateToSummaryScreen)
                }
            }

            UserAction.AddProduct -> addProduct()

            is UserAction.ImageSelected -> {
                _state.update {
                    it.copy(selectedImageByteArray = action.byteArray)
                }
            }

            UserAction.FetchAllProducts -> getAllProducts()
            UserAction.FetchMyProducts -> getMyProducts()
            UserAction.FetchCategories -> getCategories()
            is UserAction.DeleteProduct -> deleteProduct(action.productId)
            is UserAction.ProductSelected -> {
                _state.update {
                    it.copy(selectedProduct = action.product)
                }
                viewModelScope.launch {
                    _uiEvent.send(NavigateToEditProductScreen(action.product.id))
                }
            }

            is UserAction.FetchProduct -> fetchProduct(action.productId)
            UserAction.UpdateProduct -> updateProduct()
            is UserAction.BuyProduct -> buyProduct()
            is UserAction.ViewedProductFromAllProducts -> {
                _state.update {
                    it.copy(selectedProduct = action.product)
                }
                viewModelScope.launch {
                    _uiEvent.send(NavigateToProductDetailScreen)
                }
            }

            is UserAction.RentProduct -> rentProduct(action.from, action.to)
        }
    }

    private fun rentProduct(from: Long, to: Long) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            productRentUseCase(
                productRentRequest = ProductRentRequest(
                    renter = sessionManager.getUserId() ?: -1,
                    product = _state.value.selectedProduct?.id ?: -1,
                    rentOption = _state.value.selectedProduct?.rentOption.orEmpty(),
                    rentPeriodStartDate = from.toIso8601String(),
                    rentPeriodEndDate = to.toIso8601String()
                )
            ).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _uiEvent.send(ShowToast("Product rented successfully"))
                        _uiEvent.send(NavigateToAllProductScreen)
                    }

                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = dataResult.error.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun toggleBiometricLogin() {
        if (biometricManager.isBiometricSupported()) {
            val isEnabled = sessionManager.isBiometricLoginEnabled()
            sessionManager.setBiometricLoginEnabled(!isEnabled)
            _state.update { it.copy(isBiometricEnabled = !isEnabled) }
            val message =
                if (!isEnabled) "Biometric login enabled" else "Biometric login disabled"
            viewModelScope.launch {
                _uiEvent.send(ShowToast(message))
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(ShowToast("Biometric not supported on this device"))
            }
        }
    }

    private fun logout() {
        sessionManager.clearSession()
        viewModelScope.launch {
            _uiEvent.send(Logout)
        }
    }

    private fun addProduct() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            addProductUseCase(
                sellerId = sessionManager.getUserId() ?: -1,
                title = state.value.productTitle,
                description = state.value.productDesc,
                selectedCategories = state.value.selectedCategories,
                productImage = state.value.selectedImageByteArray!!,
                purchasePrice = state.value.purchasePrice,
                rentPrice = state.value.rentPrice,
                rentOption = state.value.selectedRentalOption?.apiValue.orEmpty()
            ).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _uiEvent.send(ShowToast("Product added successfully"))
                        _uiEvent.send(NavigateToAllProductScreen)
                    }

                    is DataResult.Error -> {
                        _state.update { state ->
                            state.copy(error = dataResult.error.toString(), isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun getAllProducts() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getAllProductsUseCase().collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _state.update { state ->
                            state.copy(allProducts = dataResult.data)
                        }
                    }

                    is DataResult.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _state.update { state ->
                            state.copy(error = dataResult.error.toString())
                        }
                    }
                }
            }
        }
    }

    private fun deleteProduct(productId: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            deleteProductUseCase(productId).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _uiEvent.send(ShowToast("Product deleted successfully"))
                        getMyProducts()
                    }

                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = dataResult.error.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getMyProducts() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMyProductsUseCase(sessionManager.getUserId() ?: -1).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _state.update { state ->
                            state.copy(myProducts = dataResult.data)
                        }
                    }

                    is DataResult.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _state.update { state ->
                            state.copy(error = dataResult.error.toString())
                        }
                    }
                }
            }
        }
    }

    private fun getCategories() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            categoryUseCase().collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _state.update { state ->
                            state.copy(categories = dataResult.data)
                        }
                    }

                    is DataResult.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _state.update { state ->
                            state.copy(error = dataResult.error.toString())
                        }
                    }
                }
            }
        }
    }

    private fun fetchProduct(productId: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getProductUseCase(productId).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { data ->
                            data.copy(
                                isLoading = false,
                                error = null,
                                selectedProduct = dataResult.data,
                                productTitle = dataResult.data.title,
                                productDesc = dataResult.data.description,
                                purchasePrice = dataResult.data.purchasePrice,
                                rentPrice = dataResult.data.rentPrice,
                                selectedCategories = dataResult.data.categories.mapNotNull { categoryString ->
                                    _state.value.categories.find { it.value == categoryString }
                                },
                                selectedRentalOption = RentOption.entries.find { it.apiValue == dataResult.data.rentOption }
                            )
                        }
                    }

                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = dataResult.error.toString()
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(InternalAPI::class)
    private fun updateProduct() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val product = _state.value.selectedProduct
            if (product != null) {
                updateProductUseCase(
                    productId = product.id,
                    formData = MultiPartFormDataContent(
                        formData {
                            append("seller", sessionManager.getUserId() ?: -1)
                            append("title", _state.value.productTitle.ifEmpty { product.title })
                            append(
                                "description",
                                _state.value.productDesc.ifEmpty { product.description })
                            _state.value.selectedCategories.forEach { category ->
                                append("categories", category.value)
                            }
                            append(
                                "purchase_price",
                                _state.value.purchasePrice.ifEmpty { product.purchasePrice })
                            append(
                                "rent_price",
                                _state.value.rentPrice.ifEmpty { product.rentPrice })
                            append(
                                "rent_option",
                                _state.value.selectedRentalOption?.apiValue?.ifEmpty { product.rentOption }
                                    ?: product.rentOption
                            )
                        }
                    )
                ).collect { dataResult ->
                    when (dataResult) {
                        is DataResult.Success -> {
                            _state.update { it.copy(isLoading = false, error = null) }
                            _uiEvent.send(ShowToast("Product updated successfully"))
                            _uiEvent.send(PopBackStack)
                        }

                        is DataResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = dataResult.error.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun buyProduct() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            buyProductUseCase(
                sessionManager.getUserId() ?: -1,
                _state.value.selectedProduct?.id ?: -1
            ).collect { dataResult ->
                when (dataResult) {
                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false, error = null) }
                        _uiEvent.send(ShowToast("Product purchased successfully"))
                        _uiEvent.send(NavigateToAllProductScreen)
                    }

                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = dataResult.error.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}

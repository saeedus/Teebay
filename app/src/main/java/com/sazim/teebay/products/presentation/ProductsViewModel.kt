/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.core.presentation.BiometricAuthManager
import com.sazim.teebay.products.domain.usecase.AddProductUseCase
import com.sazim.teebay.products.domain.usecase.DeleteProductUseCase
import com.sazim.teebay.products.domain.usecase.GetAllProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetCategoriesUseCase
import com.sazim.teebay.products.domain.usecase.GetMyProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetProductUseCase
import com.sazim.teebay.products.domain.usecase.UpdateProductUseCase
import com.sazim.teebay.products.presentation.ProductsEvents.*
import kotlinx.coroutines.channels.Channel
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
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
    private val updateProductUseCase: UpdateProductUseCase
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
                    it.copy(productSummary = action.title)
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
                    it.copy(selectedImageUri = action.byteArray)
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
        }
    }

    private fun toggleBiometricLogin() {
        if (biometricManager.isBiometricSupported()) {
            val isEnabled = sessionManager.isBiometricLoginEnabled()
            sessionManager.setBiometricLoginEnabled(!isEnabled)
            _state.update { it.copy(isBiometricEnabled = !isEnabled) }
            val message = if (!isEnabled) "Biometric login enabled" else "Biometric login disabled"
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
                description = state.value.productSummary,
                selectedCategories = state.value.selectedCategories,
                productImage = state.value.selectedImageUri!!,
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
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                selectedProduct = dataResult.data
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

    private fun updateProduct() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val product = _state.value.selectedProduct
            if (product != null) {
                updateProductUseCase(
                    productId = product.id,
                    formData = MultiPartFormDataContent(
                        formData {
                            append("title", _state.value.productTitle.ifEmpty { product.title })
                            append(
                                "description",
                                _state.value.productSummary.ifEmpty { product.description })
                            append(
                                "purchasePrice",
                                _state.value.purchasePrice.ifEmpty { product.purchasePrice })
                            append(
                                "rentPrice",
                                _state.value.rentPrice.ifEmpty { product.rentPrice })
                            append(
                                "rentOption",
                                _state.value.selectedRentalOption?.apiValue.orEmpty()
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
}

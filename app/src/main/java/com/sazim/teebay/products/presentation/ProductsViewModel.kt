/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.core.presentation.BiometricAuthManager
import com.sazim.teebay.products.domain.usecase.GetAllProductsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val sessionManager: SessionManager,
    private val biometricManager: BiometricAuthManager,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(ProductsState(isBiometricEnabled = sessionManager.isBiometricLoginEnabled()))
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ProductsEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAllProducts()
    }

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
                    _uiEvent.send(ProductsEvents.NavigateToCategorySelectScreen)
                }
            }

            is UserAction.CategoriesSelected -> {
                _state.update {
                    it.copy(categories = action.categories)
                }
            }

            UserAction.OnBackPressed -> {
                viewModelScope.launch {
                    _uiEvent.send(ProductsEvents.PopBackStack)
                }
            }

            UserAction.NextPressedFromCategoryScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(ProductsEvents.NavigateToDescScreen)
                }
            }

            UserAction.NextPressedFromSummaryScreen -> {
                viewModelScope.launch {
                    _uiEvent.send(ProductsEvents.NavigateToProductPicScreen)
                }
            }

            is UserAction.ProductSummaryTyped -> {
                _state.update {
                    it.copy(productSummary = action.title)
                }
            }

            UserAction.NextPressedFromImgUpload -> {
                viewModelScope.launch {
                    _uiEvent.send(ProductsEvents.NavigateToProductPriceScreen)
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
                    _uiEvent.send(ProductsEvents.NavigateToSummaryScreen)
                }
            }
        }
    }

    private fun toggleBiometricLogin() {
        if (biometricManager.isBiometricSupported()) {
            val isEnabled = sessionManager.isBiometricLoginEnabled()
            sessionManager.setBiometricLoginEnabled(!isEnabled)
            _state.update { it.copy(isBiometricEnabled = !isEnabled) }
            val message = if (!isEnabled) "Biometric login enabled" else "Biometric login disabled"
            viewModelScope.launch {
                _uiEvent.send(ProductsEvents.ShowToast(message))
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(ProductsEvents.ShowToast("Biometric not supported on this device"))
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
        viewModelScope.launch {
            _uiEvent.send(ProductsEvents.Logout)
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase().collect {
                when (it) {
                    is DataResult.Success -> {
                        _state.update { state ->
                            state.copy(allProducts = it.data)
                        }
                    }

                    is DataResult.Error -> {
                        _state.update { state ->
                            state.copy(error = it.error.toString())
                        }
                    }
                }
            }
        }
    }
}

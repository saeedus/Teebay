/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.presentation.BiometricAuthManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val sessionManager: SessionManager,
    private val biometricManager: BiometricAuthManager
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
}

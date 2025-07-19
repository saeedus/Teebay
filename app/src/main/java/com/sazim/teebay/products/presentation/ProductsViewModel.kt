/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.local.SessionManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val sessionManager: SessionManager,
    private val fingerprintManager: com.sazim.teebay.core.presentation.FingerprintManager
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ProductsEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: UserAction) {
        when (action) {
            UserAction.Logout -> logout()
            UserAction.Fingerprint -> toggleFingerprint()
        }
    }

    private fun toggleFingerprint() {
        if (fingerprintManager.isBiometricSupported()) {
            val isEnabled = sessionManager.isFingerprintLoginEnabled()
            sessionManager.setFingerprintLoginEnabled(!isEnabled)
            val message = if (!isEnabled) "Fingerprint login enabled" else "Fingerprint login disabled"
            viewModelScope.launch {
                _uiEvent.send(ProductsEvents.ShowToast(message))
            }
        } else {
            viewModelScope.launch {
                _uiEvent.send(ProductsEvents.ShowToast("Fingerprint not supported on this device"))
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

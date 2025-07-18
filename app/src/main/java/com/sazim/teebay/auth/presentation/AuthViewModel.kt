/*
 * Created by Saeedus Salehin on 18/7/25, 9:25 AM.
 */

package com.sazim.teebay.auth.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<AuthEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: UserAction) {
        when (action) {
            is UserAction.OnEmailTyped -> {
                _state.update { it.copy(email = action.email) }
            }

            is UserAction.OnPasswordTyped -> {
                _state.update { it.copy(password = action.password) }
            }

            UserAction.OnSignUpTapped -> {
                _state.update { it.copy(isLogin = false) }
            }

            UserAction.OnSignInTapped -> {
                _state.update { it.copy(isLogin = true) }
            }

            is UserAction.OnFirstNameTyped -> {
                _state.update { it.copy(firstName = action.firstName) }
            }

            is UserAction.OnLastNameTyped -> {
                _state.update { it.copy(lastName = action.lastName) }
            }

            is UserAction.OnAddressTyped -> {
                _state.update { it.copy(address = action.address) }
            }

            is UserAction.OnPhoneNumberTyped -> {
                _state.update { it.copy(phoneNumber = action.phoneNumber) }
            }

            is UserAction.OnConfirmPasswordTyped -> {
                _state.update { it.copy(confirmPassword = action.confirmPassword) }
            }
        }
    }
}
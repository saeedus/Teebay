/*
 * Created by Saeedus Salehin on 18/7/25, 9:25 AM.
 */

package com.sazim.teebay.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.model.SignUpRequest
import com.sazim.teebay.auth.domain.usecase.SignUpUseCase
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.domain.DataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

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

            UserAction.OnSignUpTapped -> register()
            else -> Unit
        }
    }

    private fun register() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            signUpUseCase(
                SignUpRequest(
                    email = state.value.email,
                    password = state.value.password,
                    firstName = state.value.firstName,
                    lastName = state.value.lastName,
                    address = state.value.address,
                    firebaseConsoleManagerToken = "" // This will be replaced by the FcmTokenProvider in the repository
                )
            ).collect { result ->
                when (result) {
                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.error.toString()
                            )
                        }
                        Log.d("SIGNUP", "signup: ${result.error}")
                    }

                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        sessionManager.saveAuthToken(result.data.firebaseConsoleManagerToken)
                        sessionManager.saveUserId(result.data.id)
                        _uiEvent.send(AuthEvents.NavigateToMyProducts)
                        Log.d("SIGNUP", "signup: ${result.data}")
                    }
                }
            }
        }
    }
}

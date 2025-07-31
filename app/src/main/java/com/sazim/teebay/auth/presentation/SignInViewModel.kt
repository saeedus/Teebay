/*
 * Created by Saeedus Salehin on 18/7/25, 9:25 AM.
 */

package com.sazim.teebay.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sazim.teebay.auth.domain.usecase.LoginUseCase
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.core.domain.DataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<AuthEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkForBiometricLogin()
    }

    fun onAction(action: UserAction) {
        when (action) {
            is UserAction.OnEmailTyped -> {
                _state.update { it.copy(email = action.email) }
            }

            is UserAction.OnPasswordTyped -> {
                _state.update { it.copy(password = action.password) }
            }

            UserAction.ShowSignUpForm -> {
                _state.update { it.copy(isLogin = false) }
            }

            UserAction.ShowSignInForm -> {
                _state.update { it.copy(isLogin = true) }
            }

            UserAction.OnSignInTapped -> login()

            UserAction.OnBiometricSuccess -> handleBiometricSuccess()

            UserAction.ShowBiometricPrompt -> {
                viewModelScope.launch {
                    _uiEvent.send(AuthEvents.ShowBiometricPrompt)
                }
            }
            else -> Unit
        }
    }

    private fun handleBiometricSuccess() {
        viewModelScope.launch {
            _uiEvent.send(AuthEvents.NavigateToMyProducts)
        }
    }

    private fun checkForBiometricLogin() {
        if (sessionManager.isBiometricLoginEnabled() && sessionManager.isLoggedIn()) {
            _state.update { it.copy(shouldShowBiometricPrompt = true) }
            viewModelScope.launch { _uiEvent.send(AuthEvents.ShowBiometricPrompt) }
        }
    }

    private fun login() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            loginUseCase(state.value.email, state.value.password).collect { result ->
                when (result) {
                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.error.toString()
                            )
                        }
                        Log.d("LOGIN", "login: ${result.error}")
                    }

                    is DataResult.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        sessionManager.saveAuthToken(result.data.user.firebaseConsoleManagerToken)
                        sessionManager.saveUserId(result.data.user.id)
                        _uiEvent.send(AuthEvents.NavigateToMyProducts)
                        Log.d("LOGIN", "login: ${result.data}")
                    }
                }
            }
        }
    }
}

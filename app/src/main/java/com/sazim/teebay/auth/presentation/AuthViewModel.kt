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
        }
    }
}
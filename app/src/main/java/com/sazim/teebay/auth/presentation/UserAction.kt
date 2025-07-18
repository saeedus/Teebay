/*
 * Created by Saeedus Salehin on 18/7/25, 10:15 AM.
 */

package com.sazim.teebay.auth.presentation

sealed interface UserAction {
    data class OnEmailTyped(val email: String) : UserAction
    data class OnPasswordTyped(val password: String) : UserAction
}
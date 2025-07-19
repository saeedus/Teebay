/*
 * Created by Saeedus Salehin on 18/7/25, 10:17 AM.
 */

package com.sazim.teebay.auth.presentation

sealed interface AuthEvents {
    data object NavigateToMyProducts : AuthEvents
    data object ShowBiometricPrompt : AuthEvents
}
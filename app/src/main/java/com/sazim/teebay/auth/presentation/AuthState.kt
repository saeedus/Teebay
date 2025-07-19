/*
 * Created by Saeedus Salehin on 18/7/25, 10:13 AM.
 */

package com.sazim.teebay.auth.presentation

data class AuthState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLogin: Boolean = true,
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val confirmPassword: String = "",
    val shouldShowFingerprintPrompt: Boolean = false
)
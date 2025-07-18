/*
 * Created by Saeedus Salehin on 18/7/25, 10:15 AM.
 */

package com.sazim.teebay.auth.presentation

sealed interface UserAction {
    data class OnEmailTyped(val email: String) : UserAction
    data class OnPasswordTyped(val password: String) : UserAction
    object ShowSignUpForm : UserAction
    object ShowSignInForm : UserAction
    object OnSignInTapped : UserAction
    object OnSignUpTapped : UserAction
    data class OnFirstNameTyped(val firstName: String) : UserAction
    data class OnLastNameTyped(val lastName: String) : UserAction
    data class OnAddressTyped(val address: String) : UserAction
    data class OnPhoneNumberTyped(val phoneNumber: String) : UserAction
    data class OnConfirmPasswordTyped(val confirmPassword: String) : UserAction
}
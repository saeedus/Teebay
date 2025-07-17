/*
 * Created by Saeedus Salehin on 18/7/25, 12:51 AM.
 */

package com.sazim.teebay.auth.presentation.navigation

sealed class AuthNavRoutes(val route: String) {
    data object AuthScreen : AuthNavRoutes("auth_screen")
}
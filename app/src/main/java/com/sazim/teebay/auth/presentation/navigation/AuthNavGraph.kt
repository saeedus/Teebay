/*
 * Created by Saeedus Salehin on 18/7/25, 12:36 AM.
 */

package com.sazim.teebay.auth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sazim.teebay.auth.presentation.AuthState
import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.auth.presentation.screens.AuthScreen

@Composable
fun AuthNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AuthNavRoutes,
    authViewModel: AuthViewModel,
    authState: AuthState
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(route = AuthNavRoutes.AuthScreen.route) {
            AuthScreen(modifier = modifier, state = authState, viewModel = authViewModel)
        }
    }
}
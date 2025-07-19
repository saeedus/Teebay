/*
 * Created by Saeedus Salehin on 19/7/25, 12:06 PM.
 */

package com.sazim.teebay.products.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sazim.teebay.products.presentation.AllProductScreen
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.screens.MyProductsScreen

@Composable
fun ProductNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: ProductNavRoutes,
    viewModel: ProductsViewModel,
    state: ProductsState
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(route = ProductNavRoutes.MyProductsScreen.route) {
            MyProductsScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(route = ProductNavRoutes.AllProductsScreen.route) {
            AllProductScreen()
        }
    }
}
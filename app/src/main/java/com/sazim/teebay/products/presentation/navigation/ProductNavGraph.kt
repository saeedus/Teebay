/*
 * Created by Saeedus Salehin on 19/7/25, 12:06 PM.
 */

package com.sazim.teebay.products.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.screens.AddProductCategoryScreen
import com.sazim.teebay.products.presentation.screens.AddProductDescScreen
import com.sazim.teebay.products.presentation.screens.AddProductPhotoUploadScreen
import com.sazim.teebay.products.presentation.screens.AddProductPriceSelectionScreen
import com.sazim.teebay.products.presentation.screens.AddProductSummaryScreen
import com.sazim.teebay.products.presentation.screens.AddProductTitleScreen
import com.sazim.teebay.products.presentation.screens.AllProductScreen
import com.sazim.teebay.products.presentation.screens.EditProductScreen
import com.sazim.teebay.products.presentation.screens.MyDealsScreen
import com.sazim.teebay.products.presentation.screens.MyProductsScreen
import com.sazim.teebay.products.presentation.screens.ProductDetailScreen

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
            AllProductScreen(
                modifier = modifier,
                state = state,
                viewModel = viewModel
            )
        }

        composable(route = ProductNavRoutes.AddProductTitleScreen.route) {
            AddProductTitleScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(
            route = ProductNavRoutes.ProductDetailScreen.route
        ) {
            ProductDetailScreen(modifier = modifier, viewModel = viewModel, state = state)
        }

        composable(route = ProductNavRoutes.AddProductCategoryScreen.route) {
            AddProductCategoryScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(route = ProductNavRoutes.AddProductDescScreen.route) {
            AddProductDescScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(route = ProductNavRoutes.AddProductPhotoUploadScreen.route) {
            AddProductPhotoUploadScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(route = ProductNavRoutes.AddProductPriceSelectionScreen.route) {
            AddProductPriceSelectionScreen(
                modifier = modifier,
                state = state,
                viewModel = viewModel
            )
        }

        composable(route = ProductNavRoutes.AddProductSummaryScreen.route) {
            AddProductSummaryScreen(modifier = modifier, state = state, viewModel = viewModel)
        }

        composable(route = ProductNavRoutes.EditProductScreen.route) {
            EditProductScreen(
                modifier = modifier,
                state = state,
                viewModel = viewModel
            )
        }

        composable(route = ProductNavRoutes.MyDealsScreen.route) {
            MyDealsScreen(modifier = modifier)
        }

    }
}
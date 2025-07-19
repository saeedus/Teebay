/*
 * Created by Saeedus Salehin on 19/7/25, 12:06 PM.
 */

package com.sazim.teebay.products.presentation.navigation

sealed class ProductNavRoutes(val route: String) {
    data object MyProductsScreen : ProductNavRoutes("my_products")
    data object AllProductsScreen : ProductNavRoutes("all_products")
}
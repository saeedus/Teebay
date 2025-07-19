/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.components.ProductCard

@Composable
fun MyProductsScreen(
    modifier: Modifier = Modifier,
    state: ProductsState,
    viewModel: ProductsViewModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProductCard(
            product = Product(
                id = 1,
                seller = 1,
                title = "Product 1",
                description = "Description for Product 1",
                categories = listOf("Category 1", "Category 2"),
                productImage = "https://example.com/product1.jpg",
                purchasePrice = "100.00",
                rentPrice = "50.00",
                rentOption = "Daily",
                datePosted = "11 march"
            )
        ) { }
    }
}
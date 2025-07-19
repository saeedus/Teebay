/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Text(
            text = "MY PRODUCTS",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
        )
        LazyColumn {
            items(count = 10, itemContent = {
                ProductCard(
                    product = Product(
                        id = 1,
                        seller = 1,
                        title = "Product 1",
                        description = "AuraLink AI Home Hub Pro: Your Smart Home Redefined \uD83C\uDFE1\n" +
                                "Experience the ultimate in smart living with the AuraLink AI Home Hub Pro. This revolutionary device seamlessly integrates all your smart home tech, from lights and thermostats to security cameras, into one intuitive system. Boasting cutting-edge AI, it learns your habits for effortless voice and touch control. Enjoy crystal-clear audio and enhance your home security with local processing for ultimate privacy. Its elegant design fits any décor, and setup is incredibly simple. This unit is in pristine condition, ready to transform your home into an intelligent sanctuary.",
                        categories = listOf("Category 1", "Category 2"),
                        productImage = "https://example.com/product1.jpg",
                        purchasePrice = "100.00",
                        rentPrice = "50.00",
                        rentOption = "Daily",
                        datePosted = "11 march"
                    )
                )
            })
        }
    }
}
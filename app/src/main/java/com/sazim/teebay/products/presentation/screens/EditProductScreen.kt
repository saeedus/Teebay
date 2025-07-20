package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sazim.teebay.core.presentation.ui.components.InputField
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction

@Composable
fun EditProductScreen(
    modifier: Modifier = Modifier,
    state: ProductsState,
    viewModel: ProductsViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(UserAction.FetchProduct(state.selectedProduct?.id ?: -1))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Product",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.selectedProduct != null -> {
                InputField(
                    value = state.productTitle.ifEmpty { state.selectedProduct.title },
                    onValueChange = {
                        viewModel.onAction(UserAction.ProductTitleTyped(it))
                    },
                    label = "Product Title"
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    value = state.productSummary.ifEmpty { state.selectedProduct.description },
                    onValueChange = {
                        viewModel.onAction(UserAction.ProductSummaryTyped(it))
                    },
                    label = "Product Description"
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    value = state.purchasePrice.ifEmpty { state.selectedProduct.purchasePrice.toString() },
                    onValueChange = {
                        viewModel.onAction(UserAction.PurchasePriceTyped(it))
                    },
                    label = "Purchase Price"
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    value = state.rentPrice.ifEmpty { state.selectedProduct.rentPrice.toString() },
                    onValueChange = {
                        viewModel.onAction(UserAction.RentPriceTyped(it))
                    },
                    label = "Rent Price"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.onAction(UserAction.UpdateProduct)
                }) {
                    Text(text = "Save Changes")
                }
            }
        }
    }
}

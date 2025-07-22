package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel,
    state: ProductsState
) {

    LaunchedEffect(Unit) {
        viewModel.onAction(UserAction.FetchCategories)
        viewModel.onAction(UserAction.FetchProduct(state.selectedProduct?.id ?: -1))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = state.productTitle, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Category: ${state.selectedCategories.map { it.label }}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rent Price: $${state.rentPrice} ${state.selectedRentalOption?.uiDisplay}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Purchase Price: $${state.purchasePrice}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Details: ${state.productDesc}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.onAction(UserAction.BuyProduct)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Buy")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Handle Rent */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Rent")
        }
    }
}

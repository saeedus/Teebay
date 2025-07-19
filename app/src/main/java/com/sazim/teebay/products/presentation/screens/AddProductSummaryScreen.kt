/*
 * Created by Saeedus Salehin on 19/7/25, 8:53 PM.
 */

package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import com.sazim.teebay.products.presentation.components.BackNextNavigationRow
import com.sazim.teebay.products.presentation.components.StepProgressIndicator

@Composable
fun AddProductSummaryScreen(
    modifier: Modifier = Modifier,
    state: ProductsState,
    viewModel: ProductsViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(32.dp))

        StepProgressIndicator(
            totalSteps = state.totalStepsToAddProduct,
            currentStep = 6
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(20.dp))

        state.selectedImageUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Title: ${state.productTitle}", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = "Categories:\n${state.categories.joinToString()}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = "Description: ${state.productSummary}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )


        Text(
            text = "Price: $${state.purchasePrice}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = "To rent: $${state.rentPrice}", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = state.selectedRentalOption,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.weight(2f))

        BackNextNavigationRow(
            onBack = { viewModel.onAction(UserAction.OnBackPressed) },
            onNext = { viewModel.onAction(UserAction.AddProduct) },
            nextText = "Submit",
            isNextEnabled = true
        )

        Spacer(Modifier.height(32.dp))
    }
}
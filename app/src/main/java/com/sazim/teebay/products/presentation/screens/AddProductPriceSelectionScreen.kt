/*
 * Created by Saeedus Salehin on 19/7/25, 8:52 PM.
 */

package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sazim.teebay.core.presentation.ui.components.InputField
import com.sazim.teebay.products.domain.utils.RentOption
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import com.sazim.teebay.products.presentation.components.BackNextNavigationRow
import com.sazim.teebay.products.presentation.components.SimpleSpinner
import com.sazim.teebay.products.presentation.components.StepProgressIndicator

@Composable
fun AddProductPriceSelectionScreen(
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
            currentStep = 5
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Select price",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(8.dp))


        InputField(
            value = state.purchasePrice,
            onValueChange = {
                viewModel.onAction(UserAction.PurchasePriceTyped(it))
            },
            keyboardType = KeyboardType.Number,
            label = ""
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Rent",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(8.dp))

        InputField(
            value = state.rentPrice,
            onValueChange = {
                viewModel.onAction(UserAction.RentPriceTyped(it))
            },
            keyboardType = KeyboardType.Number,
            label = ""
        )

        Spacer(Modifier.height(8.dp))

        SimpleSpinner(
            selectedOption = state.selectedRentalOption ?: RentOption.PER_DAY,
            onOptionSelected = {
                viewModel.onAction(UserAction.RentOptionSelected(it))
            }
        )

        Spacer(Modifier.weight(2f))

        BackNextNavigationRow(
            onBack = { viewModel.onAction(UserAction.OnBackPressed) },
            onNext = { viewModel.onAction(UserAction.NextPressedFromPriceScreen) },
            isNextEnabled = state.purchasePrice.isNotBlank() && state.rentPrice.isNotBlank() && state.selectedRentalOption != null
        )

        Spacer(Modifier.height(32.dp))
    }
}
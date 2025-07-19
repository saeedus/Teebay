/*
 * Created by Saeedus Salehin on 19/7/25, 8:41 PM.
 */

package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sazim.teebay.core.presentation.ui.components.InputField
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import com.sazim.teebay.products.presentation.components.StepProgressIndicator

@Composable
fun AddProductTitleScreen(
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
            currentStep = 1
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Select a title for your product",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(16.dp))

        InputField(
            value = state.productTitle,
            onValueChange = {
                viewModel.onAction(UserAction.ProductTitleTyped(it))
            },

            label = ""
        )

        Spacer(Modifier.weight(2f))

        Button(
            enabled = state.productTitle.isNotBlank(),
            onClick = {
                viewModel.onAction(UserAction.NextPressedFromTitleScreen)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Next")
        }

        Spacer(Modifier.height(32.dp))
    }
}
/*
 * Created by Saeedus Salehin on 19/7/25, 8:51 PM.
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import com.sazim.teebay.products.presentation.components.BackNextNavigationRow
import com.sazim.teebay.products.presentation.components.CategorySpinner
import com.sazim.teebay.products.presentation.components.StepProgressIndicator

@Composable
fun AddProductCategoryScreen(
    modifier: Modifier = Modifier,
    state: ProductsState,
    viewModel: ProductsViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(UserAction.FetchCategories)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(32.dp))

        StepProgressIndicator(
            totalSteps = state.totalStepsToAddProduct,
            currentStep = 2
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Select categories",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(16.dp))

        CategorySpinner(
            selectedCategories = state.selectedCategories,
            allCategories = state.categories,
            onCategoriesSelected = { viewModel.onAction(UserAction.CategoriesSelected(it)) }
        )

        Spacer(Modifier.weight(2f))

        BackNextNavigationRow(
            onBack = { viewModel.onAction(UserAction.OnBackPressed) },
            onNext = { viewModel.onAction(UserAction.NextPressedFromCategoryScreen) },
            isNextEnabled = state.selectedCategories.isNotEmpty()
        )

        Spacer(Modifier.height(32.dp))
    }
}
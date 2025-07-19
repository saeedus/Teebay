/*
 * Created by Saeedus Salehin on 19/7/25, 10:20 PM.
 */

package com.sazim.teebay.products.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackNextNavigationRow(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNext: () -> Unit,
    isNextEnabled: Boolean,
    backText: String = "Back",
    nextText: String = "Next"
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onBack) {
            Text(backText)
        }

        Button(
            onClick = onNext,
            enabled = isNextEnabled
        ) {
            Text(nextText)
        }
    }
}

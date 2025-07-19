/*
 * Created by Saeedus Salehin on 19/7/25, 8:59 PM.
 */

package com.sazim.teebay.products.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.sazim.teebay.R

@Composable
fun StepProgressIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    val progress = (currentStep.toFloat() / totalSteps).coerceIn(0f, 1f)

    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = colorResource(R.color.purple_500),
        trackColor = colorResource(R.color.purple_200).copy(alpha = 0.8f)
    )
}
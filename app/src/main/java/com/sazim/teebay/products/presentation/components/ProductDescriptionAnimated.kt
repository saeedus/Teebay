/*
 * Created by Saeedus Salehin on 19/7/25, 5:59 PM.
 */

package com.sazim.teebay.products.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun ProductDescriptionAnimated(
    descriptionText: String
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }
    val collapsedTextLength = 120
    val showMoreDetailsButton = descriptionText.length > collapsedTextLength

    Column {
        Text(
            text = buildAnnotatedString {
                if (isDescriptionExpanded) {
                    append(descriptionText)
                } else {
                    if (showMoreDetailsButton) {
                        append(descriptionText.take(collapsedTextLength))
                        append("... ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("More Details")
                        }
                    } else {
                        append(descriptionText)
                    }
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .animateContentSize()
                .then(
                    if (showMoreDetailsButton) Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isDescriptionExpanded = !isDescriptionExpanded
                    } else Modifier
                )
        )
    }
}

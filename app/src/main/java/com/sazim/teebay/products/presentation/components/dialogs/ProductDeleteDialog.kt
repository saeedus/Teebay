/*
 * Created by Saeedus Salehin on 20/7/25, 3:44 PM.
 */

package com.sazim.teebay.products.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductDeleteDialog(
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text("Delete Product") },
        text = { Text("Are you sure you want to delete this product?") },
        confirmButton = {
            Button(
                onClick = { onClick() }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("No")
            }
        }
    )
}
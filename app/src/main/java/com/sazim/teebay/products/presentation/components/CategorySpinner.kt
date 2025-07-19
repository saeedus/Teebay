/*
 * Created by Saeedus Salehin on 19/7/25, 9:42 PM.
 */

package com.sazim.teebay.products.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun CategorySpinner(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        "ELECTRONICS",
        "FURNITURE",
        "HOME APPLIANCES",
        "SPORTING GOODS",
        "OUTDOOR",
        "TOYS"
    )

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    if (state.isFocused && !expanded) {
                        expanded = true
                    }
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}

/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import android.net.Uri
import com.sazim.teebay.products.domain.model.Product

data class ProductsState(
    val isLoading: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val allProducts: List<Product> = emptyList(),
    val myProducts: List<Product> = emptyList(),
    val error: String? = null,

    //Add product
    val totalStepsToAddProduct: Int = 6,
    val productTitle: String = "",
    val purchasePrice: String = "",
    val rentPrice: String = "",
    val productSummary: String = "",
    val categories: List<String> = emptyList(),
    val selectedRentalOption: String = "",
    val selectedImageUri: Uri? = null
)
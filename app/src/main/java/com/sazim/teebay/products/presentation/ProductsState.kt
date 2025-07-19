/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import com.sazim.teebay.products.domain.model.Product

data class ProductsState(
    val isLoading: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val allProducts: List<Product> = emptyList(),
    val myProducts: List<Product> = emptyList(),
    val error: String? = null
)
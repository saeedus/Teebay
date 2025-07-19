/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

data class ProductsState(
    val isLoading: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val products: List<com.sazim.teebay.products.domain.model.Product> = emptyList(),
    val error: String? = null
)
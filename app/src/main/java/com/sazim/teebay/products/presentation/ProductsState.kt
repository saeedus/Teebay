/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.utils.RentOption

data class ProductsState(
    val isLoading: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val allProducts: List<Product> = emptyList(),
    val myProducts: List<Product> = emptyList(),
    val error: String? = null,
    val selectedProduct: Product? = null,

    //Add product
    val totalStepsToAddProduct: Int = 6,
    val productTitle: String = "",
    val purchasePrice: String = "",
    val rentPrice: String = "",
    val productDesc: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategories: List<Category> = emptyList(),
    val selectedRentalOption: RentOption? = null,
    val selectedImageByteArray: ByteArray? = null
)
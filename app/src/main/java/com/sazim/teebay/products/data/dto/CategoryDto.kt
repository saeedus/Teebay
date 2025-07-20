/*
 * Created by Saeedus Salehin on 20/7/25, 2:35 PM.
 */

package com.sazim.teebay.products.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val label: String,
    val value: String
)
package com.sazim.teebay.products.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val seller: Int,
    val title: String,
    val description: String,
    val categories: List<String>,
    @SerialName("product_image")
    val productImage: String,
    @SerialName("purchase_price")
    val purchasePrice: String,
    @SerialName("rent_price")
    val rentPrice: String,
    @SerialName("rent_option")
    val rentOption: String,
    @SerialName("date_posted")
    val datePosted: String
)

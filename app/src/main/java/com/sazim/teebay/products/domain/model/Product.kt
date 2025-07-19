package com.sazim.teebay.products.domain.model

data class Product(
    val id: Int,
    val seller: Int,
    val title: String,
    val description: String,
    val categories: List<String>,
    val productImage: String,
    val purchasePrice: String,
    val rentPrice: String,
    val rentOption: String,
    val datePosted: String
)
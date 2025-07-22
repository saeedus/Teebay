/*
 * Created by Saeedus Salehin on 22/7/25, 3:57 PM.
 */

package com.sazim.teebay.products.domain.model

data class ProductRentResponse(
    val id: Int,
    val renterId: Int,
    val sellerId: Int,
    val productId: Int,
    val rentOption: String,
    val rentPeriodStartDate: String,
    val rentPeriodEndDate: String,
    val totalPrice: String,
    val rentDate: String
)

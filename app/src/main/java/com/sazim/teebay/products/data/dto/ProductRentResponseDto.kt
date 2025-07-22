/*
 * Created by Saeedus Salehin on 22/7/25, 3:57 PM.
 */

package com.sazim.teebay.products.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRentResponseDto(
    val id: Int,
    val renter: Int,
    val seller: Int,
    val product: Int,
    @SerialName("rent_option")
    val rentOption: String,
    @SerialName("rent_period_start_date")
    val rentPeriodStartDate: String,
    @SerialName("rent_period_end_date")
    val rentPeriodEndDate: String,
    @SerialName("total_price")
    val totalPrice: String,
    @SerialName("rent_date")
    val rentDate: String
)

/*
 * Created by Saeedus Salehin on 22/7/25, 3:56 PM.
 */

package com.sazim.teebay.products.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRentRequest(
    val renter: Int,
    val product: Int,
    @SerialName("rent_option")
    val rentOption: String,
    @SerialName("rent_period_start_date")
    val rentPeriodStartDate: String,
    @SerialName("rent_period_end_date")
    val rentPeriodEndDate: String
)

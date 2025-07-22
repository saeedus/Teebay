/*
 * Created by Saeedus Salehin on 22/7/25, 3:14 PM.
 */

package com.sazim.teebay.products.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDto(
    val id: Int,
    val buyer: Int,
    val seller: Int,
    val product: Int,
    @SerialName("purchase_date")
    val purchaseDate: String
)

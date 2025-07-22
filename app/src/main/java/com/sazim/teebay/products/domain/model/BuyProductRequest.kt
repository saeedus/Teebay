/*
 * Created by Saeedus Salehin on 22/7/25, 3:13 PM.
 */

package com.sazim.teebay.products.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BuyProductRequest(
    val buyer: Int,
    val product: Int
)
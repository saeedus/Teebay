/*
 * Created by Saeedus Salehin on 22/7/25, 3:19 PM.
 */

package com.sazim.teebay.products.domain.model

data class ProductBuyResponse(
    val id: Int,
    val buyerId: Int,
    val sellerId: Int,
    val productId: Int,
    val purchaseDate: String
)

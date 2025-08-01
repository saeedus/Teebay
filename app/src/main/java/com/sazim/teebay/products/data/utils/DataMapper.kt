/*
 * Created by Saeedus Salehin on 19/7/25, 5:18 PM.
 */

package com.sazim.teebay.products.data.utils

import com.sazim.teebay.core.utils.toFormattedDate
import com.sazim.teebay.products.data.dto.CategoryDto
import com.sazim.teebay.products.data.dto.PurchaseDto
import com.sazim.teebay.products.data.dto.ProductDto
import com.sazim.teebay.products.data.dto.RentalDto
import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.model.ProductBuyResponse
import com.sazim.teebay.products.domain.model.ProductRentResponse


fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        seller = seller,
        title = title,
        description = description,
        categories = categories,
        productImage = productImage,
        purchasePrice = purchasePrice,
        rentPrice = rentPrice,
        rentOption = rentOption,
        datePosted = datePosted.toFormattedDate()
    )
}

fun CategoryDto.toDomain(): Category {
    return Category(
        label = label,
        value = value
    )
}

fun PurchaseDto.toDomain(): ProductBuyResponse {
    return ProductBuyResponse(
        id = id,
        buyerId = buyer,
        sellerId = seller,
        productId = product,
        purchaseDate = purchaseDate.toFormattedDate()

    )
}

fun RentalDto.toDomain(): ProductRentResponse {
    return ProductRentResponse(
        id = id,
        renterId = renter,
        sellerId = seller,
        productId = product,
        rentOption = rentOption,
        rentPeriodStartDate = rentPeriodStartDate,
        rentPeriodEndDate = rentPeriodEndDate,
        totalPrice = totalPrice,
        rentDate = rentDate.toFormattedDate()
    )
}

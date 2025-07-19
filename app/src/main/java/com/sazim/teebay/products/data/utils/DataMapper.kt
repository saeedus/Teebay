/*
 * Created by Saeedus Salehin on 19/7/25, 5:18 PM.
 */

package com.sazim.teebay.products.data.utils

import com.sazim.teebay.products.data.dto.ProductDto
import com.sazim.teebay.products.domain.model.Product


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
        datePosted = datePosted
    )
}
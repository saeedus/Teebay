/*
 * Created by Saeedus Salehin on 22/7/25, 4:01 PM.
 */

package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.products.domain.model.ProductRentRequest
import com.sazim.teebay.products.domain.repository.ProductRepository

class ProductRentUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        productRentRequest: ProductRentRequest
    ) = productRepository.rentProduct(productRentRequest)
}
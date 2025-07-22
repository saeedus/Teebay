package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.products.domain.repository.ProductRepository

class BuyProductUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        buyerId: Int,
        productId: Int
    ) = productRepository.buyProduct(buyerId, productId)
}
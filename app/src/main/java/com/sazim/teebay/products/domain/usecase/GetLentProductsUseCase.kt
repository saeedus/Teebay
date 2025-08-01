package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.products.domain.repository.ProductRepository

class GetLentProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(userId: Int) = productRepository.getLentProducts(userId)
}

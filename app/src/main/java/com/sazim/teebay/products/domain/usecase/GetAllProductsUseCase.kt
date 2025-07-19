package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.products.domain.ProductRepository

class GetAllProductsUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.getAllProducts()
}
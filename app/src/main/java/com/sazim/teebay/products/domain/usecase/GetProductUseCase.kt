package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(productId: Int): Flow<DataResult<Product, DataError.Network>> {
        return productRepository.getProduct(productId)
    }
}
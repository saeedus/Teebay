package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.repository.ProductRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import kotlinx.coroutines.flow.Flow

class UpdateProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(productId: Int, formData: MultiPartFormDataContent): Flow<DataResult<Product, DataError.Network>> {
        return productRepository.updateProduct(productId, formData)
    }
}
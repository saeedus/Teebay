/*
 * Created by Saeedus Salehin on 20/7/25, 3:48 PM.
 */

package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class DeleteProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Flow<DataResult<Unit, DataError.Network>> =
        repository.deleteProduct(productId)
}
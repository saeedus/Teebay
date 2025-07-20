/*
 * Created by Saeedus Salehin on 20/7/25, 1:42 PM.
 */

package com.sazim.teebay.products.domain.usecase

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<DataResult<List<Category>, DataError.Network>> =
        productRepository.getCategories()

}
/*
 * Created by Saeedus Salehin on 19/7/25, 6:57 PM.
 */

package com.sazim.teebay.products.domain

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getMyProducts(sellerID: Int): Flow<DataResult<List<Product>, DataError.Network>>
    suspend fun getAllProducts(): Flow<DataResult<List<Product>, DataError.Network>>
}
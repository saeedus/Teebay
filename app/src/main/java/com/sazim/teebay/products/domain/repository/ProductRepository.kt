package com.sazim.teebay.products.domain.repository

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Product
import io.ktor.client.request.forms.MultiPartFormDataContent
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getMyProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>>
    suspend fun getAllProducts(): Flow<DataResult<List<Product>, DataError.Network>>
    suspend fun addProduct(formData: MultiPartFormDataContent): Flow<DataResult<Product, DataError.Network>>
}
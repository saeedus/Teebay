package com.sazim.teebay.products.domain.repository

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.model.Product
import io.ktor.client.request.forms.MultiPartFormDataContent
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getMyProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>>
    suspend fun getAllProducts(): Flow<DataResult<List<Product>, DataError.Network>>
    suspend fun addProduct(formData: MultiPartFormDataContent): Flow<DataResult<Product, DataError.Network>>
    suspend fun getCategories(): Flow<DataResult<List<Category>, DataError.Network>>
    suspend fun deleteProduct(id: Int): Flow<DataResult<Unit, DataError.Network>>
    suspend fun getProduct(id: Int): Flow<DataResult<Product, DataError.Network>>
    suspend fun updateProduct(id: Int, formData: MultiPartFormDataContent): Flow<DataResult<Product, DataError.Network>>
}
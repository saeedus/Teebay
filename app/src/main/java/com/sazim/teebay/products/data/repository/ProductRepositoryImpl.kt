/*
 * Created by Saeedus Salehin on 19/7/25, 7:09 PM.
 */

package com.sazim.teebay.products.data.repository

import com.sazim.teebay.core.data.remote.ApiConfig
import com.sazim.teebay.core.data.repository.BaseRepository
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.data.dto.ProductDto
import com.sazim.teebay.products.data.utils.toDomain
import com.sazim.teebay.products.domain.repository.ProductRepository
import com.sazim.teebay.products.domain.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(apiConfig: ApiConfig, httpClient: HttpClient) :
    BaseRepository(apiConfig, httpClient),
    ProductRepository {
    override suspend fun getMyProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        makeApiRequest<List<ProductDto>, List<Product>>(
            method = HttpMethod.Get,
            endpoint = "products?userId=$userId",
            transform = { it.map { it -> it.toDomain() } }
        )

    override suspend fun getAllProducts(): Flow<DataResult<List<Product>, DataError.Network>> =
        makeApiRequest<List<ProductDto>, List<Product>>(
            method = HttpMethod.Get,
            endpoint = "products/",
            transform = { it.map { it -> it.toDomain() } }
        )

    override suspend fun addProduct(formData: MultiPartFormDataContent): Flow<DataResult<Product, DataError.Network>> =
        makeApiRequest<ProductDto, Product>(
            method = HttpMethod.Post,
            endpoint = "products/",
            requestBody = formData,
            transform = { it.toDomain() }
        )
}
/*
 * Created by Saeedus Salehin on 19/7/25, 7:09 PM.
 */

package com.sazim.teebay.products.data.repository

import com.sazim.teebay.core.data.remote.ApiConfig
import com.sazim.teebay.core.data.repository.BaseRepository
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.data.dto.CategoryDto
import com.sazim.teebay.products.data.dto.PurchaseDto
import com.sazim.teebay.products.data.dto.ProductDto
import com.sazim.teebay.products.data.dto.RentalDto
import com.sazim.teebay.products.data.utils.toDomain
import com.sazim.teebay.products.domain.model.BuyProductRequest
import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.repository.ProductRepository
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.model.ProductBuyResponse
import com.sazim.teebay.products.domain.model.ProductRentRequest
import com.sazim.teebay.products.domain.model.ProductRentResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class ProductRepositoryImpl(apiConfig: ApiConfig, httpClient: HttpClient) :
    BaseRepository(apiConfig, httpClient),
    ProductRepository {
    override suspend fun getMyProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        makeApiRequest<List<ProductDto>, List<Product>>(
            method = HttpMethod.Get,
            endpoint = "products/",
            transform = { it -> it.filter { it.seller == userId }.map { it -> it.toDomain() } }
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

    override suspend fun getCategories(): Flow<DataResult<List<Category>, DataError.Network>> =
        makeApiRequest<List<CategoryDto>, List<Category>>(
            method = HttpMethod.Get,
            endpoint = "products/categories/",
            transform = { it.map { it -> it.toDomain() } }
        )

    override suspend fun deleteProduct(id: Int): Flow<DataResult<Unit, DataError.Network>> =
        makeApiRequest<Unit, Unit>(
            method = HttpMethod.Delete,
            endpoint = "products/${id}/",
            transform = {}
        )

    override suspend fun getProduct(id: Int): Flow<DataResult<Product, DataError.Network>> =
        makeApiRequest<ProductDto, Product>(
            method = HttpMethod.Get,
            endpoint = "products/${id}/",
            transform = { it.toDomain() }
        )

    override suspend fun updateProduct(
        id: Int,
        formData: MultiPartFormDataContent
    ): Flow<DataResult<Product, DataError.Network>> = makeApiRequest<ProductDto, Product>(
        method = HttpMethod.Patch,
        endpoint = "products/${id}/",
        requestBody = formData,
        transform = { it.toDomain() }
    )

    override suspend fun buyProduct(
        buyerId: Int,
        productId: Int
    ): Flow<DataResult<ProductBuyResponse, DataError.Network>> =
        makeApiRequest<PurchaseDto, ProductBuyResponse>(
            method = HttpMethod.Post,
            endpoint = "transactions/purchases/",
            requestBody = BuyProductRequest(buyerId, productId),
            transform = { it.toDomain() }
        )

    override suspend fun rentProduct(productRentRequest: ProductRentRequest): Flow<DataResult<ProductRentResponse, DataError.Network>> =
        makeApiRequest<RentalDto, ProductRentResponse>(
            method = HttpMethod.Post,
            endpoint = "transactions/rentals/",
            requestBody = productRentRequest,
            transform = { it.toDomain() }
        )

    override suspend fun getBoughtProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        flow {
            val purchaseResult = makeApiRequest<List<PurchaseDto>, List<PurchaseDto>>(
                method = HttpMethod.Get,
                endpoint = "transactions/purchases/",
                transform = { it }
            ).firstOrNull()

            when (purchaseResult) {
                is DataResult.Success -> {
                    val boughtProductIds = purchaseResult.data
                        .filter { it.buyer == userId }
                        .map { it.product }

                    val semaphore = Semaphore(permits = 10)

                    val products = coroutineScope {
                        boughtProductIds.map { productId ->
                            async {
                                semaphore.withPermit {
                                    makeApiRequest<ProductDto, Product>(
                                        method = HttpMethod.Get,
                                        endpoint = "products/$productId/",
                                        transform = { it.toDomain() }
                                    ).firstOrNull()
                                }
                            }
                        }.awaitAll()
                            .mapNotNull { result -> (result as? DataResult.Success)?.data }
                    }

                    emit(DataResult.Success(products))
                }

                is DataResult.Error -> emit(DataResult.Error(purchaseResult.error))
                null -> emit(DataResult.Error(DataError.Network.UNKNOWN))
            }
        }


    override suspend fun getSoldProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        flow {
            val purchaseResult = makeApiRequest<List<PurchaseDto>, List<PurchaseDto>>(
                method = HttpMethod.Get,
                endpoint = "transactions/purchases/",
                transform = { it }
            ).firstOrNull()

            when (purchaseResult) {
                is DataResult.Success -> {
                    val soldProductIds = purchaseResult.data
                        .filter { it.seller == userId }
                        .map { it.product }
                        .distinct()

                    val semaphore = Semaphore(permits = 10)

                    val products = coroutineScope {
                        soldProductIds.map { productId ->
                            async {
                                semaphore.withPermit {
                                    makeApiRequest<ProductDto, Product>(
                                        method = HttpMethod.Get,
                                        endpoint = "products/$productId/",
                                        transform = { it.toDomain() }
                                    ).firstOrNull()
                                }
                            }
                        }.awaitAll()
                            .mapNotNull { result -> (result as? DataResult.Success)?.data }
                    }

                    emit(DataResult.Success(products))
                }

                is DataResult.Error -> emit(DataResult.Error(purchaseResult.error))
                null -> emit(DataResult.Error(DataError.Network.UNKNOWN))
            }
        }


    override suspend fun getBorrowedProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        flow {
            val rentalResult = makeApiRequest<List<RentalDto>, List<RentalDto>>(
                method = HttpMethod.Get,
                endpoint = "transactions/rentals/",
                transform = { it }
            ).firstOrNull()

            when (rentalResult) {
                is DataResult.Success -> {
                    val borrowedProductIds = rentalResult.data
                        .filter { it.renter == userId }
                        .map { it.product }
                        .distinct()

                    val semaphore = Semaphore(permits = 10)

                    val products = coroutineScope {
                        borrowedProductIds.map { productId ->
                            async {
                                semaphore.withPermit {
                                    makeApiRequest<ProductDto, Product>(
                                        method = HttpMethod.Get,
                                        endpoint = "products/$productId/",
                                        transform = { it.toDomain() }
                                    ).firstOrNull()
                                }
                            }
                        }.awaitAll()
                            .mapNotNull { result -> (result as? DataResult.Success)?.data }
                    }

                    emit(DataResult.Success(products))
                }

                is DataResult.Error -> emit(DataResult.Error(rentalResult.error))
                null -> emit(DataResult.Error(DataError.Network.UNKNOWN))
            }
        }


    override suspend fun getLentProducts(userId: Int): Flow<DataResult<List<Product>, DataError.Network>> =
        flow {
            val rentalResult = makeApiRequest<List<RentalDto>, List<RentalDto>>(
                method = HttpMethod.Get,
                endpoint = "transactions/rentals/",
                transform = { it }
            ).firstOrNull()

            when (rentalResult) {
                is DataResult.Success -> {
                    val lentProductIds = rentalResult.data
                        .filter { it.renter == userId }
                        .map { it.product }
                        .distinct()

                    val semaphore = Semaphore(permits = 10)

                    val products = coroutineScope {
                        lentProductIds.map { productId ->
                            async {
                                semaphore.withPermit {
                                    makeApiRequest<ProductDto, Product>(
                                        method = HttpMethod.Get,
                                        endpoint = "products/$productId/",
                                        transform = { it.toDomain() }
                                    ).firstOrNull()
                                }
                            }
                        }.awaitAll()
                            .mapNotNull { result -> (result as? DataResult.Success)?.data }
                    }

                    emit(DataResult.Success(products))
                }

                is DataResult.Error -> emit(DataResult.Error(rentalResult.error))
                null -> emit(DataResult.Error(DataError.Network.UNKNOWN))
            }
        }
}
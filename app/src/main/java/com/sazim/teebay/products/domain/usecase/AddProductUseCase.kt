/*
 * Created by Saeedus Salehin on 20/7/25, 1:25 AM.
 */

package com.sazim.teebay.products.domain.usecase

import com.google.firebase.Timestamp
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.products.domain.repository.ProductRepository
import com.sazim.teebay.products.domain.model.Product
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow

class AddProductUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        categories: List<String>,
        productImage: ByteArray,
        purchasePrice: String,
        rentPrice: String,
        rentOption: String
    ): Flow<DataResult<Product, DataError.Network>> {
        val formData = MultiPartFormDataContent(
            formData {
                append("seller", 1)
                append("title", title)
                append("description", description)
                append("categories[]", categories)
                append("purchase_price", purchasePrice)
                append("rent_price", rentPrice)
                append("rent_option", rentOption)
                append("product_image", productImage, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=\"${Timestamp.now()}\"")
                })
            }
        )
        return productRepository.addProduct(formData)
    }
}
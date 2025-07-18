/*
 * Created by Saeedus Salehin on 18/7/25, 11:33 PM.
 */

package com.sazim.teebay.core.data.repository

import com.sazim.teebay.core.data.remote.ApiConfig
import com.sazim.teebay.core.data.remote.safeCall
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import com.sazim.teebay.core.domain.map
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository(
    val apiConfig: ApiConfig,
    val httpClient: HttpClient
) {
    protected inline fun <reified T : Any, R : Any> makeApiRequest(
        method: HttpMethod,
        endpoint: String,
        requestBody: Any? = null,
        queryParameters: Map<String, String> = emptyMap(),
        pathParameters: Map<String, String> = emptyMap(),
        noinline transform: (T) -> R
    ): Flow<DataResult<R, DataError.Network>> = flow {
        emit(
            safeCall<T> {
                var fullUrl = "${apiConfig.baseUrl}$endpoint"
                pathParameters.forEach { (key, value) ->
                    fullUrl = fullUrl.replace("{$key}", value)
                }

                val requestBuilder: HttpRequestBuilder.() -> Unit = {
                    url(fullUrl)
                    queryParameters.forEach { (key, value) ->
                        parameter(key, value)
                    }
//                    token?.let { headers.append(HttpHeaders.Authorization, "Bearer $it") }

                    requestBody?.let {
                        when (it) {
                            is MultiPartFormDataContent -> {
                                contentType(ContentType.MultiPart.FormData)
                                setBody(it)
                            }
                            else -> {
                                contentType(ContentType.Application.Json)
                                setBody(it)
                            }
                        }
                    }
                }

                when (method) {
                    HttpMethod.Get -> httpClient.get(requestBuilder).body()
                    HttpMethod.Post -> httpClient.post(requestBuilder).body()
                    HttpMethod.Put -> httpClient.put(requestBuilder).body()
                    HttpMethod.Patch -> httpClient.patch(requestBuilder).body()
                    HttpMethod.Delete -> httpClient.delete(requestBuilder).body()
                    else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
                }
            }.map { dto: T ->
                transform(dto)
            }
        )
    }.flowOn(Dispatchers.IO)
}
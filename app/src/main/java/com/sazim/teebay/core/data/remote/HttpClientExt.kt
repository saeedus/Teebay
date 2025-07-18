/*
 * Created by Saeedus Salehin on 18/7/25, 12:27 PM.
 */

package com.sazim.teebay.core.data.remote

import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): DataResult<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return DataResult.Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return DataResult.Error(DataError.Network.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return DataResult.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): DataResult<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                DataResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                DataResult.Error(DataError.Network.SERIALIZATION)
            }
        }

        400 -> DataResult.Error(DataError.Network.BAD_REQUEST)
        401 -> DataResult.Error(DataError.Network.UNAUTHORIZED)
        404 -> DataResult.Error(DataError.Network.NOT_FOUND)
        408 -> DataResult.Error(DataError.Network.REQUEST_TIMEOUT)
        413 -> DataResult.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> DataResult.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> DataResult.Error(DataError.Network.SERVER_ERROR)
        else -> DataResult.Error(DataError.Network.UNKNOWN)
    }
}
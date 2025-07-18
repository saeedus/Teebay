/*
 * Created by Saeedus Salehin on 18/7/25, 12:30 PM.
 */

package com.sazim.teebay.core.domain

sealed interface DataResult<out D, out E : Error> {
    data class Success<out D>(val data: D) : DataResult<D, Nothing>
    data class Error<out E : com.sazim.teebay.core.domain.Error>(val error: E) :
        DataResult<Nothing, E>
}

inline fun <T, E : Error, R> DataResult<T, E>.map(map: (T) -> R): DataResult<R, E> {
    return when (this) {
        is DataResult.Error -> DataResult.Error(error)
        is DataResult.Success -> DataResult.Success(map(data))
    }
}

fun <T, E : Error> DataResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> DataResult<T, E>.onSuccess(action: (T) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Error -> this
        is DataResult.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> DataResult<T, E>.onError(action: (E) -> Unit): DataResult<T, E> {
    return when (this) {
        is DataResult.Error -> {
            action(error)
            this
        }

        is DataResult.Success -> this
    }
}

typealias EmptyResult<E> = DataResult<Unit, E>
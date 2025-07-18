/*
 * Created by Saeedus Salehin on 18/7/25, 12:29 PM.
 */

package com.sazim.teebay.core.domain


interface DataError : Error {
    enum class Network : DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        NOT_FOUND,
        REQUEST_TIMEOUT,
        PAYLOAD_TOO_LARGE,
        TOO_MANY_REQUESTS,
        SERIALIZATION,
        SERVER_ERROR,
        NO_INTERNET,
        UNKNOWN
    }
}
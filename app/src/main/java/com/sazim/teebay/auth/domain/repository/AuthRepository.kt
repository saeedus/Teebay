/*
 * Created by Saeedus Salehin on 18/7/25, 11:36 PM.
 */

package com.sazim.teebay.auth.domain.repository

import com.sazim.teebay.auth.domain.model.AuthResponse
import com.sazim.teebay.auth.domain.model.SignUpRequest
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Flow<DataResult<AuthResponse, DataError.Network>>

    suspend fun signUp(
        request: SignUpRequest
    ): Flow<DataResult<AuthResponse, DataError.Network>>
}
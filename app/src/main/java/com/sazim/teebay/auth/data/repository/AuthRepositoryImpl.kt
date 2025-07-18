/*
 * Created by Saeedus Salehin on 19/7/25, 12:42 AM.
 */

package com.sazim.teebay.auth.data.repository

import com.sazim.teebay.auth.data.FcmTokenProvider
import com.sazim.teebay.auth.data.dto.AuthResponseDto
import com.sazim.teebay.auth.data.utils.toDomain
import com.sazim.teebay.auth.domain.model.AuthRequest
import com.sazim.teebay.auth.domain.model.AuthResponse
import com.sazim.teebay.auth.domain.repository.AuthRepository
import com.sazim.teebay.core.data.remote.ApiConfig
import com.sazim.teebay.core.data.repository.BaseRepository
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    apiConfig: ApiConfig,
    httpClient: HttpClient,
    private val fcmTokenProvider: FcmTokenProvider
) : BaseRepository(apiConfig, httpClient), AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<DataResult<AuthResponse, DataError.Network>> {
        return makeApiRequest<AuthResponseDto, AuthResponse>(
            method = HttpMethod.Post,
            endpoint = "users/login/",
            requestBody = AuthRequest(email, password, fcmTokenProvider.getToken().orEmpty()),
            transform = { it.toDomain() }
        )
    }
}
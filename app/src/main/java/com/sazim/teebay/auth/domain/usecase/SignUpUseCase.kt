package com.sazim.teebay.auth.domain.usecase

import com.sazim.teebay.auth.domain.model.SignUpRequest
import com.sazim.teebay.auth.domain.model.SignUpResponse
import com.sazim.teebay.auth.domain.repository.AuthRepository
import com.sazim.teebay.core.domain.DataError
import com.sazim.teebay.core.domain.DataResult
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(request: SignUpRequest): Flow<DataResult<SignUpResponse, DataError.Network>> =
        authRepository.signUp(request)
}

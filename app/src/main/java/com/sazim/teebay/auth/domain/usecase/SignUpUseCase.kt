package com.sazim.teebay.auth.domain.usecase

import com.sazim.teebay.auth.domain.model.SignUpRequest
import com.sazim.teebay.auth.domain.repository.AuthRepository

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(request: SignUpRequest) = authRepository.signUp(request)
}

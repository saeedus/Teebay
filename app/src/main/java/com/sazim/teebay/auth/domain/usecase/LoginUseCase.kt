/*
 * Created by Saeedus Salehin on 19/7/25, 12:48 AM.
 */

package com.sazim.teebay.auth.domain.usecase

import com.sazim.teebay.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.signIn(email = email, password = password)
}
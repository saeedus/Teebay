/*
 * Created by Saeedus Salehin on 19/7/25, 12:40 AM.
 */

package com.sazim.teebay.auth.data.utils

import com.sazim.teebay.auth.data.dto.AuthResponseDto
import com.sazim.teebay.auth.domain.model.AuthResponse

fun AuthResponseDto.toDomain(): AuthResponse {
    return AuthResponse(
        email = email,
        password = password,
        fcmToken = fcmToken
    )
}
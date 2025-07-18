/*
 * Created by Saeedus Salehin on 19/7/25, 12:40 AM.
 */

package com.sazim.teebay.auth.data.utils

import com.sazim.teebay.auth.data.dto.LoginResponseDto
import com.sazim.teebay.auth.domain.model.LoginResponse

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        email = email,
        password = password,
        fcmToken = fcmToken
    )
}
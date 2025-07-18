package com.sazim.teebay.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val email: String,
    val password: String,
    @SerialName("fcm_token") val fcmToken: String
)
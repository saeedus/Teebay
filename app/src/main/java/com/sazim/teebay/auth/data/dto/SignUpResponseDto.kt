package com.sazim.teebay.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDto(
    val id: Int,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val address: String,
    @SerialName("firebase_console_manager_token") val firebaseConsoleManagerToken: String,
    val password: String,
    @SerialName("date_joined") val dateJoined: String
)

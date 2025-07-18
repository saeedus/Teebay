/*
 * Created by Saeedus Salehin on 19/7/25, 12:39 AM.
 */

package com.sazim.teebay.auth.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val email: String,
    val password: String,
    @SerialName("fcm_token") val fcmToken: String
)
/*
 * Created by Saeedus Salehin on 19/7/25, 12:39 AM.
 */

package com.sazim.teebay.auth.domain.model

data class LoginResponse(
    val email: String,
    val password: String,
    val fcmToken: String
)
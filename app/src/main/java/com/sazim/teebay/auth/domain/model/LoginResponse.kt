/*
 * Created by Saeedus Salehin on 19/7/25, 12:39 AM.
 */

package com.sazim.teebay.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val user: User
)
package com.sazim.teebay.auth.domain.local

interface SessionManager {
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun isLoggedIn(): Boolean
    fun clearSession()
    fun isBiometricLoginEnabled(): Boolean
    fun setBiometricLoginEnabled(enabled: Boolean)
    fun saveUserId(userId: Int)
    fun getUserId(): Int?
}
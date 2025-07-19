package com.sazim.teebay.auth.domain.local

interface SessionManager {
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun isLoggedIn(): Boolean
    fun clearSession()
    fun isFingerprintLoginEnabled(): Boolean
    fun setFingerprintLoginEnabled(enabled: Boolean)
}
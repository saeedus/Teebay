package com.sazim.teebay.auth.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sazim.teebay.auth.domain.local.SessionManager
import androidx.core.content.edit

//TODO implement secured shared preference for auth token later

class SessionManagerImpl(context: Context) : SessionManager {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveAuthToken(token: String) {
        prefs.edit { putString(KEY_AUTH_TOKEN, token) }
    }

    override fun getAuthToken(): String? {
        return prefs.getString(KEY_AUTH_TOKEN, null)
    }

    override fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    override fun clearSession() {
        prefs.edit { clear() }
    }

    override fun isBiometricLoginEnabled(): Boolean {
        return prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false)
    }

    override fun setBiometricLoginEnabled(enabled: Boolean) {
        prefs.edit { putBoolean(KEY_BIOMETRIC_ENABLED, enabled) }
    }

    override fun saveUserId(userId: Int) {
        prefs.edit { putInt(KEY_USER_ID, userId) }
    }

    override fun getUserId(): Int? {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
        private const val KEY_USER_ID = "user_id"
    }
}

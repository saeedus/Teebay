package com.sazim.teebay.auth.data.local

import android.content.Context
import android.content.SharedPreferences
import com.sazim.teebay.auth.domain.local.SessionManager
import androidx.core.content.edit

class SessionManagerImpl(context: Context) : SessionManager {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

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
        prefs.edit { remove(KEY_AUTH_TOKEN) }
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
}

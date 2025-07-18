package com.sazim.teebay.auth.data

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

interface FcmTokenProvider {
    suspend fun getToken(): String?
}

class FcmTokenProviderImpl : FcmTokenProvider {
    override suspend fun getToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            null
        }
    }
}

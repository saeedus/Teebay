package com.sazim.teebay.core.data.remote.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token: $token")
    }
}
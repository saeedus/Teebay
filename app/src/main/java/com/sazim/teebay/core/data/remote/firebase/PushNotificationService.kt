package com.sazim.teebay.core.data.remote.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sazim.teebay.R
import com.sazim.teebay.products.presentation.ProductsActivity
import com.sazim.teebay.products.presentation.navigation.ProductNavRoutes

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "From: ${remoteMessage.from}")

        remoteMessage.data.let { data ->
            Log.d("FCM", "Message data payload: $data")
            val productId = data["product_id"]
            val title = data["title"] ?: "Teebay Notification"
            val body = data["body"] ?: "You have a new notification from Teebay."

            if (productId != null) {
                sendNotification(title, body, productId)
            } else {
                sendNotification(title, body, null)
            }
        }

        remoteMessage.notification?.let { notification ->
            Log.d("FCM", "Message Notification Body: ${notification.body}")
            if (remoteMessage.data["product_id"] == null) {
                sendNotification(
                    notification.title ?: "Teebay Notification",
                    notification.body ?: "You have a new notification from Teebay.",
                    null
                )
            }
        }
    }

    private fun sendNotification(title: String, messageBody: String, productId: String?) {
        val intent = Intent(this, ProductsActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (productId != null) {
                putExtra("product_id", productId)
                putExtra("start_destination", ProductNavRoutes.ProductDetailScreen.route)
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "Teebay_Notifications"
        val defaultSoundUri =
            android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Teebay Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
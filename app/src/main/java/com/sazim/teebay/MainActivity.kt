package com.sazim.teebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.sazim.teebay.auth.presentation.AuthActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = false

        if (isLoggedIn) {
            //TODO: Navigate to HomeActivity or Dashboard
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()
    }
}

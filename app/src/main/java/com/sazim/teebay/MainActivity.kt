package com.sazim.teebay

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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

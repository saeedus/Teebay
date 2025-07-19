package com.sazim.teebay

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sazim.teebay.auth.domain.local.SessionManager
import com.sazim.teebay.auth.presentation.AuthActivity
import com.sazim.teebay.products.presentation.ProductsActivity
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val sessionManager: SessionManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sessionManager.isLoggedIn() && sessionManager.isBiometricLoginEnabled().not()) {
            startActivity(Intent(this, ProductsActivity::class.java))
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()
    }
}

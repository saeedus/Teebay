/*
 * Created by Saeedus Salehin on 18/7/25, 1:00 AM.
 */

package com.sazim.teebay.auth.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sazim.teebay.auth.presentation.navigation.AuthNavGraph
import com.sazim.teebay.auth.presentation.navigation.AuthNavRoutes
import com.sazim.teebay.core.presentation.FingerprintManager
import com.sazim.teebay.products.presentation.ProductsActivity
import org.koin.android.ext.android.inject
import org.koin.compose.viewmodel.koinViewModel

class AuthActivity : AppCompatActivity() {

    private val fingerprintManager: FingerprintManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = koinViewModel<AuthViewModel>()
            val state by viewModel.state.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        AuthEvents.NavigateToMyProducts -> {
                            navigateToProducts()
                        }

                        AuthEvents.ShowFingerPrintPrompt -> {
                            showFingerPrintPrompt { navigateToProducts() }
                        }
                    }
                }
            }

            Scaffold { innerPadding ->
                AuthNavGraph(
                    navController = rememberNavController(),
                    startDestination = AuthNavRoutes.AuthScreen,
                    authViewModel = viewModel,
                    authState = state,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    private fun navigateToProducts() {
        val intent =
            Intent(this@AuthActivity, ProductsActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        this@AuthActivity.startActivity(intent)
    }

    private fun showFingerPrintPrompt(onSuccess: () -> Unit) {
        fingerprintManager.showBiometricPrompt(
            activity = this@AuthActivity,
            onSuccess = { onSuccess() },
            onError = { _, _ ->
                // Handle error or fallback to password
            }
        )
    }
}
/*
 * Created by Saeedus Salehin on 18/7/25, 1:00 AM.
 */

package com.sazim.teebay.auth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sazim.teebay.auth.presentation.navigation.AuthNavGraph
import com.sazim.teebay.auth.presentation.navigation.AuthNavRoutes
import org.koin.compose.viewmodel.koinViewModel

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = koinViewModel<AuthViewModel>()
            val state by viewModel.state.collectAsState()

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
}
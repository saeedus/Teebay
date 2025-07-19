/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sazim.teebay.products.presentation.navigation.ProductNavGraph
import com.sazim.teebay.products.presentation.navigation.ProductNavRoutes
import org.koin.compose.viewmodel.koinViewModel

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = koinViewModel<ProductsViewModel>()
            val state by viewModel.state.collectAsState()

            Scaffold { innerPadding ->
                ProductNavGraph(
                    navController = rememberNavController(),
                    startDestination = ProductNavRoutes.MyProductsScreen,
                    viewModel = viewModel,
                    state = state,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
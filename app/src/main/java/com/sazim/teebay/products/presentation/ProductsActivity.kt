/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sazim.teebay.products.presentation.screens.MyProductsScreen

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(innerPadding)
                ) {

                    MyProductsScreen()
                }
            }
        }
    }
}
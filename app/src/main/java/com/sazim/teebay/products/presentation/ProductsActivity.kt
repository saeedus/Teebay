/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sazim.teebay.auth.presentation.AuthActivity
import com.sazim.teebay.products.presentation.navigation.ProductNavGraph
import com.sazim.teebay.products.presentation.navigation.ProductNavRoutes
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sazim.teebay.R
import com.sazim.teebay.core.presentation.navigation.getCurrentRoute

class ProductsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = koinViewModel<ProductsViewModel>()
            val state by viewModel.state.collectAsState()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                viewModel.uiEvent.collect {
                    when (it) {
                        is ProductsEvents.Logout -> {
                            val intent = Intent(this@ProductsActivity, AuthActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is ProductsEvents.ShowToast -> {
                            Toast.makeText(this@ProductsActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        ProductsEvents.NavigateToCategorySelectScreen -> {
                            navController.navigate(ProductNavRoutes.AddProductCategoryScreen.route)
                        }

                        ProductsEvents.PopBackStack -> {
                            navController.popBackStack()
                        }

                        ProductsEvents.NavigateToSummaryScreen -> {
                            navController.navigate(ProductNavRoutes.AddProductSummaryScreen.route)
                        }

                        ProductsEvents.NavigateToProductPicScreen -> {
                            navController.navigate(ProductNavRoutes.AddProductPhotoUploadScreen.route)
                        }

                        ProductsEvents.NavigateToProductPriceScreen -> {
                            navController.navigate(ProductNavRoutes.AddProductPriceSelectionScreen.route)
                        }
                    }
                }
            }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(40.dp))

                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "All Products",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(ProductNavRoutes.AllProductsScreen.route)
                            }
                        )

                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "My Products",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(ProductNavRoutes.MyProductsScreen.route)
                            }
                        )

                        NavigationDrawerItem(
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Biometric",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Switch(
                                        checked = state.isBiometricEnabled,
                                        onCheckedChange = {
                                            viewModel.onAction(UserAction.ToggleBiometric)
                                        }
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            selected = false,
                            onClick = {
                                viewModel.onAction(UserAction.ToggleBiometric)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )

                        Spacer(Modifier.weight(1f))

                        NavigationDrawerItem(
                            label = {
                                Text(
                                    "Logout",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            selected = false,
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = colorResource(id = R.color.red).copy(
                                    alpha = 0.2f
                                ),
                            ),
                            onClick = {
                                viewModel.onAction(UserAction.Logout)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )

                        Spacer(Modifier.height(40.dp))
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Teebay") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if (getCurrentRoute(navController) == ProductNavRoutes.MyProductsScreen.route) FloatingActionButton(
                            onClick = {
                                navController.navigate(ProductNavRoutes.AddProductTitleScreen.route)
                            },
                            shape = CircleShape,
                            containerColor = colorResource(R.color.purple_200)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_add),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                ) { innerPadding ->
                    ProductNavGraph(
                        navController = navController,
                        startDestination = ProductNavRoutes.MyProductsScreen,
                        viewModel = viewModel,
                        state = state,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
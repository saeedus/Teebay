/*
 * Created by Saeedus Salehin on 19/7/25, 6:37 PM.
 */

package com.sazim.teebay.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun getCurrentRoute(navController: NavController): String {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""
    return currentRoute.substringBefore("?").substringBefore("/")
}
/*
 * Created by Saeedus Salehin on 19/7/25, 12:22 PM.
 */

package com.sazim.teebay.products.presentation

sealed interface UserAction {
    data object Logout : UserAction
    data object ToggleBiometric : UserAction
    data object OnBackPressed : UserAction

    //Add product
    data class ProductTitleTyped(val title: String) : UserAction
    data object NextPressedFromTitleScreen : UserAction
    data class CategoriesSelected(val categories: List<String>) : UserAction
}
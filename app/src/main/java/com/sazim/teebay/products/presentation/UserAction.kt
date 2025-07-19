/*
 * Created by Saeedus Salehin on 19/7/25, 12:22 PM.
 */

package com.sazim.teebay.products.presentation

import android.net.Uri

sealed interface UserAction {
    data object Logout : UserAction
    data object ToggleBiometric : UserAction
    data object OnBackPressed : UserAction

    //Add product
    data class ProductTitleTyped(val title: String) : UserAction
    data class PurchasePriceTyped(val title: String) : UserAction
    data class RentPriceTyped(val title: String) : UserAction
    data class ProductSummaryTyped(val title: String) : UserAction
    data object NextPressedFromTitleScreen : UserAction
    data class CategoriesSelected(val categories: List<String>) : UserAction
    data object NextPressedFromCategoryScreen : UserAction
    data object NextPressedFromSummaryScreen : UserAction
    data object NextPressedFromImgUpload : UserAction
    data object NextPressedFromPriceScreen : UserAction
    data class RentOptionSelected(val option: String) : UserAction
    data object AddProduct : UserAction
    data class ImageSelected(val url: Uri?) : UserAction
}
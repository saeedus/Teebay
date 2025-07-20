/*
 * Created by Saeedus Salehin on 19/7/25, 12:22 PM.
 */

package com.sazim.teebay.products.presentation

import com.sazim.teebay.products.domain.model.Category
import com.sazim.teebay.products.domain.model.Product
import com.sazim.teebay.products.domain.utils.RentOption

sealed interface UserAction {
    data object Logout : UserAction
    data object ToggleBiometric : UserAction
    data object OnBackPressed : UserAction
    data object FetchAllProducts : UserAction
    data object FetchMyProducts : UserAction

    //Add product
    data class ProductTitleTyped(val title: String) : UserAction
    data class PurchasePriceTyped(val title: String) : UserAction
    data class RentPriceTyped(val title: String) : UserAction
    data class ProductSummaryTyped(val title: String) : UserAction
    data object NextPressedFromTitleScreen : UserAction
    data class CategoriesSelected(val selectedCategories: List<Category>) : UserAction
    data object NextPressedFromCategoryScreen : UserAction
    data object NextPressedFromSummaryScreen : UserAction
    data object NextPressedFromImgUpload : UserAction
    data object NextPressedFromPriceScreen : UserAction
    data class RentOptionSelected(val option: RentOption) : UserAction
    data object AddProduct : UserAction
    data class ImageSelected(val byteArray: ByteArray?) : UserAction
    data object FetchCategories : UserAction
    data class DeleteProduct(val productId: Int) : UserAction
    data class ProductSelected(val product: Product) : UserAction
    data class FetchProduct(val productId: Int) : UserAction
    data object UpdateProduct : UserAction
}
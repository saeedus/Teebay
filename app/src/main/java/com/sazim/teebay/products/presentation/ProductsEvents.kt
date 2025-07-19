/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

sealed class ProductsEvents {
    data object Logout : ProductsEvents()
    data object PopBackStack : ProductsEvents()
    data class ShowToast(val message: String) : ProductsEvents()
    data object NavigateToCategorySelectScreen : ProductsEvents()
    data object NavigateToSummaryScreen : ProductsEvents()
    data object NavigateToProductPicScreen : ProductsEvents()
}

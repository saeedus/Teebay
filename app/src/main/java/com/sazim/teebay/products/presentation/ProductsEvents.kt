/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

sealed class ProductsEvents {
    data object Logout : ProductsEvents()
}

/*
 * Created by Saeedus Salehin on 19/7/25, 12:22 PM.
 */

package com.sazim.teebay.products.presentation

sealed interface UserAction {
    data object Logout : UserAction
}
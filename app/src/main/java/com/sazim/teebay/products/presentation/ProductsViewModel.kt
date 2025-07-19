/*
 * Created by Saeedus Salehin on 19/7/25, 2:37 AM.
 */

package com.sazim.teebay.products.presentation

import androidx.lifecycle.ViewModel
import com.sazim.teebay.auth.presentation.UserAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ProductsViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ProductsEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: UserAction) {
        when (action) {
            else -> {}
        }
    }
}

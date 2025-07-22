package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.sazim.teebay.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.sazim.teebay.products.presentation.components.ProductCard
import androidx.compose.runtime.LaunchedEffect
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import kotlinx.coroutines.launch

sealed class MyDealsTab(val title: String) {
    data object Bought : MyDealsTab("Bought")
    data object Sold : MyDealsTab("Sold")
    data object Borrowed : MyDealsTab("Borrowed")
    data object Lent : MyDealsTab("Lent")
}

@Composable
fun MyDealsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel,
    state: ProductsState
) {
    val tabs = listOf(MyDealsTab.Bought, MyDealsTab.Sold, MyDealsTab.Borrowed, MyDealsTab.Lent)
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        when (tabs[pagerState.currentPage]) {
            MyDealsTab.Bought -> if (state.boughtProducts.isEmpty()) viewModel.onAction(UserAction.FetchBoughtProducts)
            MyDealsTab.Sold -> if (state.soldProducts.isEmpty()) viewModel.onAction(UserAction.FetchSoldProducts)
            MyDealsTab.Borrowed -> if (state.borrowedProducts.isEmpty()) viewModel.onAction(UserAction.FetchBorrowedProducts)
            MyDealsTab.Lent -> if (state.lentProducts.isEmpty()) viewModel.onAction(UserAction.FetchLentProducts)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            tab.title, style = MaterialTheme.typography.titleSmall.copy(
                                color = colorResource(
                                    R.color.black
                                )
                            )
                        )
                    }
                )
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val products = when (tabs[page]) {
                MyDealsTab.Bought -> state.boughtProducts
                MyDealsTab.Sold -> state.soldProducts
                MyDealsTab.Borrowed -> state.borrowedProducts
                MyDealsTab.Lent -> state.lentProducts
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products) { product ->
                    ProductCard(product = product)
                }
            }
        }
    }
}
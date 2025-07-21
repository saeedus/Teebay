package com.sazim.teebay.products.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import kotlinx.coroutines.launch

sealed class MyDealsTab(val title: String) {
    data object Bought : MyDealsTab("Bought")
    data object Sold : MyDealsTab("Sold")
    data object Borrowed : MyDealsTab("Borrowed")
    data object Lent : MyDealsTab("Lent")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyDealsScreen() {
    val tabs = listOf(MyDealsTab.Bought, MyDealsTab.Sold, MyDealsTab.Borrowed, MyDealsTab.Lent)
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(tab.title) }
                )
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
            page ->
            when (tabs[page]) {
                MyDealsTab.Bought -> {
                    Text("Bought Products", style = MaterialTheme.typography.headlineMedium)
                }
                MyDealsTab.Sold -> {
                    Text("Sold Products", style = MaterialTheme.typography.headlineMedium)
                }
                MyDealsTab.Borrowed -> {
                    Text("Borrowed Products", style = MaterialTheme.typography.headlineMedium)
                }
                MyDealsTab.Lent -> {
                    Text("Lent Products", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}
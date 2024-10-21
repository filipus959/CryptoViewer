package com.filip.cryptoViewer.presentation.ui.screens.coin_ticker_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.domain.model.SortCriteria
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.model.SortOrder
import com.filip.cryptoViewer.presentation.ui.LoadableScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_ticker_list.components.CoinTickerListItem


@Composable
fun CoinTickerListScreen(
    viewModel: CoinTickerListViewModel = hiltViewModel(), goToCoinChartScreen: (String) -> Unit
) {
    val state = viewModel.state

    LoadableScreen(state) {
        CoinTickerListScreenContent(
            state = viewModel.state,
            searchQuery = viewModel.searchQuery,
            rankArrow = viewModel.getArrowForField(SortField.RANK),  // Get arrow from ViewModel
            changeArrow = viewModel.getArrowForField(SortField.CHANGE),  // Get arrow from ViewModel
            priceArrow = viewModel.getArrowForField(SortField.PRICE),  // Get arrow from ViewModel
            onSortByRank = { viewModel.updateSortCriteria(SortField.RANK) },
            onSortByChange = { viewModel.updateSortCriteria(SortField.CHANGE) },
            onSortByPrice = { viewModel.updateSortCriteria(SortField.PRICE) },
            onSearchQueryChange = viewModel::onSearchQueryUpdated,
            goToCoinChartScreen = goToCoinChartScreen
        )
    }
}

@Composable
fun CoinTickerListScreenContent(
    state: CoinTickerListState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    goToCoinChartScreen: (String) -> Unit,
    rankArrow: String,
    changeArrow: String,
    priceArrow: String,
    onSortByRank: () -> Unit,
    onSortByChange: () -> Unit,
    onSortByPrice: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    state.coins.let { coins ->
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Search Coins") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true
            )

            SortingRow(
                darkTheme = darkTheme,
                rankArrow = rankArrow,
                priceArrow = priceArrow,
                changeArrow = changeArrow,
                onSortByRank = onSortByRank,
                onSortByPrice = onSortByPrice,
                onSortByChange = onSortByChange
            )

            LazyColumn {
                items(coins) { coin ->
                    CoinTickerListItem(coin = coin, onItemClick = { goToCoinChartScreen(coin.id) })
                }
            }
        }
    }
}

@Composable
fun SortingRow(
    darkTheme: Boolean,
    rankArrow: String,
    priceArrow: String,
    changeArrow: String,
    onSortByRank: () -> Unit,
    onSortByPrice: () -> Unit,
    onSortByChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (darkTheme) MaterialTheme.colorScheme.onPrimary else Color.LightGray)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortableText(text = "#. Coin $rankArrow", onClick = onSortByRank)
        SortableText(
            text = "Price $priceArrow",
            onClick = onSortByPrice,
            paddingStart = 34.dp,
            textAlign = TextAlign.Start
        )
        SortableText(
            text = "24H Change $changeArrow", onClick = onSortByChange, textAlign = TextAlign.End
        )
    }
}

@Composable
fun SortableText(
    text: String,
    onClick: () -> Unit,
    paddingStart: Dp = 0.dp,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(start = paddingStart),
        textAlign = textAlign
    )
}





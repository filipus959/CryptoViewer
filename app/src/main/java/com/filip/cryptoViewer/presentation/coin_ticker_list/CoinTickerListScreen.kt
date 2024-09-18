package com.filip.cryptoViewer.presentation.coin_ticker_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.coin_ticker_list.components.CoinTickerListItem

@Composable
fun CoinTickerListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val  darkTheme: Boolean = isSystemInDarkTheme()
    val state = viewModel.state


    Box(modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier
               .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = viewModel.searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryUpdated(it)
                }, // Call ViewModel to update search query
                label = { Text("Search Coins") },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true
            )

            // Make the row smaller by reducing the padding
            SortingRow(
                darkTheme = darkTheme,
                rankArrow = viewModel.rankArrow,
                priceArrow = viewModel.priceArrow ,
                changeArrow = viewModel.changeArrow ,
                onSortByRank = { viewModel.sortCoinsByRank() },
                onSortByPrice = { viewModel.sortCoinsByPrice() },
                onSortByChange = { viewModel.sortCoinsByChange() })

            LazyColumn {
                items(state.coins) { coin ->
                    CoinTickerListItem(
                        coin = coin,
                        onItemClick = {
                            navController.navigate(Screen.CoinChartScreen.route + "/${coin.id}")
                        }
                    )
                }
            }

        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
        SortableText(
            text = "#. Coin $rankArrow",
            onClick = onSortByRank,
        )
        SortableText(
            text = "Price $priceArrow",
            onClick = onSortByPrice,
            paddingStart = 34.dp,
            textAlign = TextAlign.Start
        )
        SortableText(
            text = "24H Change $changeArrow",
            onClick = onSortByChange,
            textAlign = TextAlign.End
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
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(start = paddingStart),
        textAlign = textAlign
    )
}

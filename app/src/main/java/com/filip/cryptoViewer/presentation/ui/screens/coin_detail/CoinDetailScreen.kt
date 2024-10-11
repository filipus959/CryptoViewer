package com.filip.cryptoViewer.presentation.ui.screens.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.presentation.ui.LoadableScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_detail.components.CoinTag
import com.filip.cryptoViewer.presentation.ui.screens.coin_detail.components.TeamListItem
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LoadableScreen(state) {
        CoinDetailScreenContent(
            state = state
        )
    }

}

@Composable
fun CoinDetailScreenContent(
    state: CoinDetailState
) {
    state.coin?.let { coin ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(8f)
                    )
                    Text(
                        text = if (coin.isActive) "active" else "inactive",
                        color = if (coin.isActive) Color.Green else Color.Red,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .align(CenterVertically)
                            .weight(2f)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = coin.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(15.dp))
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    coin.tags.forEach { tag ->
                        CoinTag(tag = tag.name)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Team members",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(15.dp))
            }

            items(coin.team) { teamMember ->
                TeamListItem(
                    teamMember = teamMember,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                HorizontalDivider()
            }
        }
    }

}

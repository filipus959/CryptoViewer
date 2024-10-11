package com.filip.cryptoViewer.presentation.ui.screens.coin_chart


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.presentation.ui.LoadableScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components.ChartRangeMenu
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components.PriceLineChart

@Composable
fun CoinChartScreen(
    viewModel: CoinChartViewModel = hiltViewModel(),
    goToDetails: (String) -> Unit
) {
    val state = viewModel.state
    var expanded by remember { mutableStateOf(false) }

    LoadableScreen(state = state) {
        CoinChartScreenContent(
            state = state,
            expanded = expanded,
            onExpandClick = { expanded = true },
            onDismissExpand = { expanded = false },
            onSelectRange = { days ->
                viewModel.changeChartRange(days)
                expanded = false
            },
            goToDetails = goToDetails
        )
    }
}

@Composable
fun CoinChartScreenContent(
    state: CoinChartState,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    onDismissExpand: () -> Unit,
    onSelectRange: (Int) -> Unit,
    goToDetails: (String) -> Unit
) {
    state.coins?.let { coins ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 32.dp)
        ) {
            // Price Chart Title and Dropdown Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price Chart for ${state.id}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onExpandClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Range")
                    ChartRangeMenu(
                        expanded = expanded,
                        onDismissRequest = onDismissExpand,
                        onSelectRange = onSelectRange
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                PriceLineChart(prices = coins)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Market Cap Info
            Text(
                text = "Market cap: " + formatNumberWithCommas(state.marketCap) + "$",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Button to Navigate to Coin Details
            Button(
                modifier = Modifier
                    .offset((-16).dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { goToDetails(state.id) }
            ) {
                Text(text = "More info about the coin")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

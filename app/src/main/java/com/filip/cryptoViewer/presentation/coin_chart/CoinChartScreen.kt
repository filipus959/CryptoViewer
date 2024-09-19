package com.filip.cryptoViewer.presentation.coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.coin_chart.components.ChartRangeMenu
import com.filip.cryptoViewer.presentation.coin_chart.components.PriceLineChart

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinChartScreen(
    navController: NavController,
    viewModel: CoinChartViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val timestamps = viewModel.getTimeStamps()

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .navigationBarsPadding()
    ) {
        state.coins.let { coins ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, top = 32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Price Chart for ${state.id}",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(1f)
                    )

                    // Button to trigger DropdownMenu visibility
                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = "Range")
                        ChartRangeMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            onSelectRange = { days ->
                                viewModel.changeChartRange(days)
                                expanded = false
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                ) {
                    PriceLineChart(prices = coins, timestamps)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Marketcap: " + formatNumberWithCommas(state.marketCap) + "$",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Button(
                    modifier = Modifier
                        .offset((-16).dp) //half of the chart padding
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        navController.navigate(Screen.CoinDetailScreen.route + "/${state.id}")
                    },

                    ) {
                    Text(text = "More info about the coin")
                }
            }

        }
        if(state.error.isNotBlank()) {
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
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
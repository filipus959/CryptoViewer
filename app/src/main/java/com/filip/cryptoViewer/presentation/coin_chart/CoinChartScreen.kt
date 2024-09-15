package com.filip.cryptoViewer.presentation.coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.coin_chart.components.PriceLineChart

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoinChartScreen(
    navController: NavController,
    viewModel: CoinChartViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp,46.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 32.dp)
        ) {
            Text(text = "Price Chart for ${state.id}", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            // Use a lower weight value for PriceLineChart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f) // Adjust the weight to make it smaller
            ) {
                PriceLineChart(prices = state.coins)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Marketcap: " + formatNumberWithCommas(state.marketCap) + "$",
                style = MaterialTheme.typography.headlineMedium
            )
            Button(
                modifier = Modifier
                    .offset((-16).dp) //half of the chart padding
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    navController.navigate(Screen.CoinDetailScreen.route + "/${state.id}")
                }
            ) {
                Text(text = "More info about the coin")
            }

        }

    }
}
package com.filip.cryptoViewer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.coin_chart.CoinChartScreen
import com.filip.cryptoViewer.presentation.coin_detail.CoinDetailScreen
import com.filip.cryptoViewer.presentation.coin_ticker_list.CoinTickerListScreen
import com.filip.cryptoViewer.presentation.ui.theme.CryptoViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoViewerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CoinListScreen.route
                    ) {
                        composable(
                            route = Screen.CoinListScreen.route
                        ) {
                            CoinTickerListScreen(navController)
                        }
                        composable(
                            route = Screen.CoinDetailScreen.route + "/{coinId}"
                        ) {
                            CoinDetailScreen(navController)
                        }
                        composable(
                            route = Screen.CoinChartScreen.route + "/{coinId}"
                        ) {
                            CoinChartScreen(navController)
                        }
                    }
                }
            }
        }
    }
}


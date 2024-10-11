package com.filip.cryptoViewer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.CoinChartScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_detail.CoinDetailScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_ticker_list.CoinTickerListScreen

@Composable
fun NavGraph(
    navController: NavHostController) {
    NavHost(navController, startDestination = Screen.CoinTickerListScreen.route) {
        composable(Screen.CoinTickerListScreen.route) {
            CoinTickerListScreen() { coinId ->
                navController.navigate(Screen.CoinChartScreen.route + "/${coinId}") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(Screen.CoinDetailScreen.route + "/{coinId}") {
            CoinDetailScreen()
        }
        composable(Screen.CoinChartScreen.route + "/{coinId}") {
            CoinChartScreen() { coinId ->
                navController.navigate(Screen.CoinDetailScreen.route + "/${coinId}")
            }


        }
        composable(Screen.CoinConverterScreen.route) {
            CoinConverterScreen()
        }
    }
}


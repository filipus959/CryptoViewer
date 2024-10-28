package com.filip.cryptoViewer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.filip.cryptoViewer.presentation.CoinChartScreen
import com.filip.cryptoViewer.presentation.CoinConverterScreen
import com.filip.cryptoViewer.presentation.CoinDetailScreen
import com.filip.cryptoViewer.presentation.CoinTickerListScreen
import com.filip.cryptoViewer.presentation.ui.screens.coinchart.CoinChartScreen
import com.filip.cryptoViewer.presentation.ui.screens.coinconverters.CoinConverterScreen
import com.filip.cryptoViewer.presentation.ui.screens.coindetail.CoinDetailScreen
import com.filip.cryptoViewer.presentation.ui.screens.cointickerlist.CoinTickerListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = CoinTickerListScreen) {
        composable<CoinTickerListScreen> {
            CoinTickerListScreen { coinId ->
                navController.navigate(CoinChartScreen(coinId = coinId)) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable<CoinDetailScreen> {
            CoinDetailScreen()
        }
        composable<CoinChartScreen> {
            CoinChartScreen { coinId ->
                navController.navigate(CoinDetailScreen(coinId = coinId))
            }
        }
        composable<CoinConverterScreen> {
            CoinConverterScreen()
        }
    }
}

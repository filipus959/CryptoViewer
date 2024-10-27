package com.filip.cryptoViewer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.ui.screens.coinchart.CoinChartScreen
import com.filip.cryptoViewer.presentation.ui.screens.coinconverters.CoinConverterScreen
import com.filip.cryptoViewer.presentation.ui.screens.coindetail.CoinDetailScreen
import com.filip.cryptoViewer.presentation.ui.screens.cointickerlist.CoinTickerListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = Screen.CoinTickerListScreen.route) {
        composable(Screen.CoinTickerListScreen.route) {
            CoinTickerListScreen() { coinId ->
                navController.navigate(Screen.CoinChartScreen.route + "/$coinId") {
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
                navController.navigate(Screen.CoinDetailScreen.route + "/$coinId")
            }
        }
        composable(Screen.CoinConverterScreen.route) {
            CoinConverterScreen()
        }
    }
}

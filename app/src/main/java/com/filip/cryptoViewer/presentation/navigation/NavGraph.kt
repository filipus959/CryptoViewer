package com.filip.cryptoViewer.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.filip.cryptoViewer.presentation.Screen
import com.filip.cryptoViewer.presentation.coin_chart.CoinChartScreen
import com.filip.cryptoViewer.presentation.coin_detail.CoinDetailScreen
import com.filip.cryptoViewer.presentation.coin_ticker_list.CoinTickerListScreenContent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.CoinTickerListScreen.route) {
        composable(Screen.CoinTickerListScreen.route) { CoinTickerListScreenContent(navController = navController) }
        composable(Screen.CoinDetailScreen.route + "/{coinId}") { CoinDetailScreen() }
        composable(Screen.CoinChartScreen.route + "/{coinId}") { CoinChartScreen(navController) }
    }

}

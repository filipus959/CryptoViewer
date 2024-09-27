package com.filip.cryptoViewer.presentation

sealed class Screen(val route: String) {
    data object CoinTickerListScreen: Screen("coin_ticker_list_screen")
    data object CoinDetailScreen: Screen("coin_detail_screen")
    data object CoinChartScreen: Screen("coin_chart_screen")
    data object CoinConverterScreen: Screen("coin_converter_screen")
}

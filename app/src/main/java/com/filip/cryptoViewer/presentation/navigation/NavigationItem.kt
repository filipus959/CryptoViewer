package com.filip.cryptoViewer.presentation.navigation

import com.filip.cryptoViewer.R
import com.filip.cryptoViewer.presentation.CoinConverterScreen
import com.filip.cryptoViewer.presentation.CoinTickerListScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationItem<T>(val route: T, val label: String, val icon: Int) {
    @Serializable
    data object Home : NavigationItem<CoinTickerListScreen>(CoinTickerListScreen, "Home", R.drawable.baseline_home_24)

    @Serializable
    data object Converters : NavigationItem<CoinConverterScreen>(CoinConverterScreen, "Converters", R.drawable.baseline_favorite_24)
}

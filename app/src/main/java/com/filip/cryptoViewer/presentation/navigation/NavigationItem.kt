package com.filip.cryptoViewer.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.filip.cryptoViewer.presentation.Screen

sealed class NavigationItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home : NavigationItem(Screen.CoinTickerListScreen.route, "Home", Icons.Default.Home)
    data object Converters : NavigationItem(Screen.CoinConverterScreen.route, "Converters", Icons.Default.Favorite)

}
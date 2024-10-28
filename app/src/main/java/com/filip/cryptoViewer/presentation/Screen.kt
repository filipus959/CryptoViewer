package com.filip.cryptoViewer.presentation

import kotlinx.serialization.Serializable

@Serializable
object CoinTickerListScreen

@Serializable
data class CoinDetailScreen(val coinId: String)

@Serializable
data class CoinChartScreen(val coinId: String)

@Serializable
object CoinConverterScreen

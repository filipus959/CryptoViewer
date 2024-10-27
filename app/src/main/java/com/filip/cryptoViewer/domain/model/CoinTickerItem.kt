package com.filip.cryptoViewer.domain.model

data class CoinTickerItem(
    val id: String,
    val name: String,
    val usdPrice: Double,
    val percentChange24h: Double,
    val rank: Int,
    val symbol: String,
)

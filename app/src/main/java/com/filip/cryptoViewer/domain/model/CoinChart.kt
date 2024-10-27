package com.filip.cryptoViewer.domain.model

data class CoinChart(
    val coinId: String,
    val marketCap: Long,
    val price: Double,
    val timestamp: String,
    val volume24h: Long,
)

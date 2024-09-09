package com.filip.cryptoViewer.domain.model

data class CoinChart (
    val market_cap: Long,
    val price: Double,
    val timestamp: String,
    val volume_24h: Long
)
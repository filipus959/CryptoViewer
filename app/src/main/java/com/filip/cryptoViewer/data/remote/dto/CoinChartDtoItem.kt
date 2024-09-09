package com.filip.cryptoViewer.data.remote.dto

import com.filip.cryptoViewer.domain.model.CoinChart

data class CoinChartDtoItem(
    val market_cap: Long,
    val price: Double,
    val timestamp: String,
    val volume_24h: Long
)


fun CoinChartDtoItem.toCoinChart() : CoinChart {
    return CoinChart(
        market_cap = volume_24h,
        price = price,
        timestamp = timestamp,
        volume_24h = volume_24h
    )
}


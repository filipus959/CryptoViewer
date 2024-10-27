package com.filip.cryptoViewer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinChartDtoItem(
    @SerializedName("market_cap")
    val marketCap: Long,
    @SerializedName("price")
    val price: Double,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("volume_24h")
    val volume24h: Long,
    @SerializedName("coinId")
    val coinId: String,
)

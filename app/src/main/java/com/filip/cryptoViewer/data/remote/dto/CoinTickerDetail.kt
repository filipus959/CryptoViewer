package com.filip.cryptoViewer.data.remote.dto

data class CoinTickerDetail(
    val beta_value: Double,
    val circulating_supply: Int,
    val first_data_at: String,
    val id: String,
    val last_updated: String,
    val max_supply: Int,
    val name: String,
    val quotes: Quotes,
    val rank: Int,
    val symbol: String,
    val total_supply: Int
)
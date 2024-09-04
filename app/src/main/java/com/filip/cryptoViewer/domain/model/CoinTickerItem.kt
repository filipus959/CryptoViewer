package com.filip.cryptoViewer.domain.model

import com.filip.cryptoViewer.data.remote.dto.Quotes

data class CoinTickerItem(
    val beta_value: Double,
    val first_data_at: String,
    val id: String,
    val last_updated: String,
    val max_supply: Long,
    val name: String,
    val quotes: Quotes,
    val rank: Int,
    val symbol: String,
    val total_supply: Long
)


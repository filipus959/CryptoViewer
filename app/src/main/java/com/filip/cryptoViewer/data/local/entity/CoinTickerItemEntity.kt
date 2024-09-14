package com.filip.cryptoViewer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_ticker_items")
data class CoinTickerItemEntity(
    @PrimaryKey
    val id: String,
    val beta_value: Double,
    val first_data_at: String,
    val last_updated: String,
    val max_supply: Long,
    val name: String,
    val percent_change_24h: Double,
    val usdPrice: Double,
    val rank: Int,
    val symbol: String,
    val total_supply: Long
)

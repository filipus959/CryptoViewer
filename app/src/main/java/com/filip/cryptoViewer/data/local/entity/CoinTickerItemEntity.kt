package com.filip.cryptoViewer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_ticker_items")
data class CoinTickerItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "beta_value")
    val betaValue: Double,
    @ColumnInfo(name = "first_data_at")
    val firstDataAt: String,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: String,
    @ColumnInfo(name = "max_supply")
    val maxSupply: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "percent_change_24h")
    val percentChange24h: Double,
    @ColumnInfo(name = "usdPrice")
    val usdPrice: Double,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "total_supply")
    val totalSupply: Long
)

package com.filip.cryptoViewer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "coin_charts",
    indices = [Index(value = ["coinId", "timestamp"], unique = true)],
)
data class CoinChartEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "coinId")
    val coinId: String = "",
    @ColumnInfo(name = "market_cap")
    val marketCap: Long,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "timestamp")
    val timestamp: String,
    @ColumnInfo(name = "volume_24h")
    val volume24h: Long,
)

package com.filip.cryptoViewer.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(tableName = "coin_charts")\
@Entity(tableName = "coin_charts",
        indices = [Index(value = ["coinId", "timestamp"], unique = true)])

data class CoinChartEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val coinId: String = "",
    val market_cap: Long,
    val price: Double,
    val timestamp: String,
    val volume_24h: Long
)
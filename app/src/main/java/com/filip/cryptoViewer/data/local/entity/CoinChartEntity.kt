package com.filip.cryptoViewer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "coin_charts")\
@Entity(tableName = "coin_charts")

data class CoinChartEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val market_cap: Long,
    val price: Double,
    val timestamp: String,
    val volume_24h: Long
)
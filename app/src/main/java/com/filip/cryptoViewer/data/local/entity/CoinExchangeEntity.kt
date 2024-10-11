package com.filip.cryptoViewer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "coin_exchanges",primaryKeys = ["coinId", "coinId2"])
data class CoinExchangeEntity(
    @ColumnInfo(name = "coinId")
    val coinId: String,
    @ColumnInfo(name = "coinId2")
    val coinId2: String,
    @ColumnInfo(name = "price")
    val price: Double
)

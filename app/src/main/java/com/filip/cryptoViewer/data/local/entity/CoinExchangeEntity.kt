package com.filip.cryptoViewer.data.local.entity

import androidx.room.Entity


@Entity(tableName = "coin_exchanges",primaryKeys = ["coinId", "coinId2"])
data class CoinExchangeEntity(
    val coinId: String,
    val coinId2: String,
    val price: Double
)

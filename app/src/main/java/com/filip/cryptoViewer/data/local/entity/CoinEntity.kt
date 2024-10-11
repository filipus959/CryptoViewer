package com.filip.cryptoViewer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "symbol")
    val symbol: String,
)

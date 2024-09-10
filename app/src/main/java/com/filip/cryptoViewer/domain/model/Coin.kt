package com.filip.cryptoViewer.domain.model

import androidx.room.PrimaryKey

data class Coin(
    @PrimaryKey
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
)

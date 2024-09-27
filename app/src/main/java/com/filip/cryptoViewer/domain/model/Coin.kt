package com.filip.cryptoViewer.domain.model

data class Coin(
    var id: String,
    val isActive: Boolean,
    var name: String,
    val rank: Int,
    val symbol: String,
)

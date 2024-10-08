package com.filip.cryptoViewer.data.remote.dto

import com.filip.cryptoViewer.domain.model.Coin

data class CoinDto(
    val id: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

fun CoinDto.toCoin(): Coin {
    return Coin (
        id = id,
        name = name,
        isActive = is_active,
        rank = rank,
        symbol = symbol
    )
}
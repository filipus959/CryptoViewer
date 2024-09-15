package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinEntity
import com.filip.cryptoViewer.domain.model.Coin

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        name = name,
        symbol = symbol,
        isActive = isActive,
        rank = rank
    )
}

fun CoinEntity.toDomainModel(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol,
        isActive = isActive,
        rank = rank
    )
}
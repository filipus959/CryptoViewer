package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinEntity
import com.filip.cryptoViewer.domain.model.Coin

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        isActive = this.isActive,
        rank = this.rank
    )
}

fun CoinEntity.toDomainModel(): Coin {
    return Coin(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        isActive = this.isActive,
        rank = this.rank
    )
}
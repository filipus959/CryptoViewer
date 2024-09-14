package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity
import com.filip.cryptoViewer.domain.model.CoinDetail

fun CoinDetail.toEntity(): CoinDetailEntity {
    return CoinDetailEntity(
        description = this.description,
        name = this.name,
        symbol = this.symbol,
        rank = this.rank,
        tags = this.tags,
        coinId = this.coinId,
        isActive = this.isActive,
        team = this.team
    )
}

fun CoinDetailEntity.toDomainModel(): CoinDetail {
    return CoinDetail(
        description = this.description,
        name = this.name,
        symbol = this.symbol,
        rank = this.rank,
        tags = this.tags,
        coinId = this.coinId,
        isActive = this.isActive,
        team = this.team
    )
}
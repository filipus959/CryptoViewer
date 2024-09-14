package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.domain.model.CoinDetail

fun CoinDetailDto.toDbModel(): CoinDetailEntity {
    return CoinDetailEntity(
        description = description,
        name = name,
        symbol = symbol,
        rank = rank,
        tags = tags,
        coinId = id,
        isActive = is_active,
        team = team
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
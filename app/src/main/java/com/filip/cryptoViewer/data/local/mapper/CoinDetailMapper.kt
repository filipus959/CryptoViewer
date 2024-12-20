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
        isActive = isActive,
        team = team,
    )
}

fun CoinDetailEntity.toDomainModel(): CoinDetail {
    return CoinDetail(
        description = description,
        name = name,
        symbol = symbol,
        rank = rank,
        tags = tags,
        coinId = coinId,
        isActive = isActive,
        team = team,
    )
}

package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinChartEntity
import com.filip.cryptoViewer.data.remote.dto.CoinChartDtoItem
import com.filip.cryptoViewer.domain.model.CoinChart

fun CoinChartDtoItem.toDbModel(coinId: String): CoinChartEntity {
    return CoinChartEntity(
        coinId = coinId,
        price = price,
        timestamp = timestamp,
        marketCap = marketCap,
        volume24h = volume24h
    )
}

fun CoinChartEntity.toDomainModel(coinId: String): CoinChart {
    return CoinChart(
        coinId = coinId,
        marketCap = marketCap,
        price = price,
        timestamp = timestamp,
        volume24h = volume24h
    )
}
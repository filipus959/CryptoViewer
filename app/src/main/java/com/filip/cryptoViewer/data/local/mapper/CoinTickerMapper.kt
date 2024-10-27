package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import com.filip.cryptoViewer.domain.model.CoinTickerItem

fun CoinTickerDto.toDbModel() = CoinTickerItemEntity(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    percentChange24h = quotes.usd.percentChange24h,
    usdPrice = quotes.usd.price,
    firstDataAt = firstDataAt,
    lastUpdated = lastUpdated,
    maxSupply = maxSupply,
    betaValue = betaValue,
    totalSupply = totalSupply,
)

fun CoinTickerItemEntity.toDomainModel() = CoinTickerItem(
    id = id,
    name = name,
    usdPrice = usdPrice,
    rank = rank,
    symbol = symbol,
    percentChange24h = percentChange24h,
)

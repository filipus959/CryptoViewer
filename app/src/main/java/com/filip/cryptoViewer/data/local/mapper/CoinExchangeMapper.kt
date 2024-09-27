package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinExchangeEntity
import com.filip.cryptoViewer.data.remote.dto.CoinExchangeDto
import com.filip.cryptoViewer.domain.model.CoinExchange

fun CoinExchangeDto.toDbModel(): CoinExchangeEntity {
    return CoinExchangeEntity(
            coinId = base_currency_id ,
            price = price,
            coinId2 = quote_currency_id,
    )
}

fun CoinExchangeEntity.toDomainModel(): CoinExchange {
    return CoinExchange(
            price = price,
            coinId = coinId,
            coinId2 = coinId2,
    )
}
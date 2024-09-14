package com.filip.cryptoViewer.data.local.mapper

import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import com.filip.cryptoViewer.domain.model.CoinTickerItem

fun CoinTickerDto.toDbModel() = CoinTickerItemEntity(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    percent_change_24h = quotes.USD.percent_change_24h,
    usdPrice = quotes.USD.price,
    first_data_at = first_data_at,
    last_updated = last_updated,
    max_supply = max_supply,
    beta_value = beta_value,
    total_supply = total_supply
)

fun CoinTickerItemEntity.toDomainModel() = CoinTickerItem(
    id= id,
    name=name ,
    usdPrice = usdPrice,
    rank= rank,
    symbol= symbol,
    percent_change_24h = percent_change_24h,
)
package com.filip.cryptoViewer.data.remote.dto

import com.filip.cryptoViewer.domain.model.CoinTickerItem

data class CoinTickerDto(
    val beta_value: Double,
    val first_data_at: String,
    val id: String,
    val last_updated: String,
    val max_supply: Long,
    val name: String,
    val quotes: Quotes,
    val rank: Int,
    val symbol: String,
    val total_supply: Long
)

//fun CoinTickerDto.toCoinTicker(): CoinTickerItem {
//    return CoinTickerItem(
//        coinId =  id,
//        name = name,
//        description = description,
//        symbol = symbol,
//        isActive = is_active,
//        rank = rank,
//        tags = tags.map { it.name},
//        team = team
//    )
//}
fun CoinTickerDto.toCoinTicker(): CoinTickerItem {
    return CoinTickerItem(
        beta_value = beta_value,
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        quotes = quotes,
        last_updated = last_updated,
        total_supply = total_supply,
        first_data_at = first_data_at,
        max_supply = max_supply
    )
}

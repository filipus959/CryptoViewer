package com.filip.cryptoViewer.data.remote.dto

data class CoinExchangeDto(
    val amount: Int,
    val base_currency_id: String,
    val base_currency_name: String,
    val base_price_last_updated: String,
    val price: Double,
    val quote_currency_id: String,
    val quote_currency_name: String,
    val quote_price_last_updated: String
)
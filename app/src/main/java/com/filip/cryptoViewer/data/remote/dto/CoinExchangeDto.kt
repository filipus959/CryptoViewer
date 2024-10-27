package com.filip.cryptoViewer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinExchangeDto(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("base_currency_id")
    val baseCurrencyId: String,
    @SerializedName("base_currency_name")
    val baseCurrencyName: String,
    @SerializedName("base_price_last_updated")
    val basePriceLastUpdated: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quote_currency_id")
    val quoteCurrencyId: String,
    @SerializedName("quote_currency_name")
    val quoteCurrencyName: String,
    @SerializedName("quote_price_last_updated")
    val quotePriceLastUpdated: String,
)

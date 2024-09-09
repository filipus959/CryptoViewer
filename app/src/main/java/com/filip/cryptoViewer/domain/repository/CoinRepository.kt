package com.filip.cryptoViewer.domain.repository

import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinTickerItem

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
    suspend fun getTickerCoins(): List<CoinTickerItem>
    suspend fun getTickerCoinById(coinId: String): CoinTickerItem
    suspend fun getChartCoinById(coinId: String, date: String): List<CoinChart>

}
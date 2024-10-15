package com.filip.cryptoViewer.domain.repository

import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinExchange
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoinById(coinId: String): CoinDetail
    suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>>
    suspend fun getChartCoinById(coinId: String): List<CoinChart>
    suspend fun getCoinExchanges(coinId: String, coinId2: String, amount : Int) : CoinExchange
    suspend fun fetchTickerCoins()

}
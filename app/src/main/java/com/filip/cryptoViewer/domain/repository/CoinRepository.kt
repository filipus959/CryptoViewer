package com.filip.cryptoViewer.domain.repository

import com.filip.cryptoViewer.domain.model.Coin
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinExchange
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinById(coinId: String): CoinDetail
    suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>>
//    suspend fun getTickerCoinById(coinId: String): CoinTickerItem
    suspend fun getChartCoinById(coinId: String): List<CoinChart>
    suspend fun getCoinExchanges(coinId: String, coinId2: String) : CoinExchange
    suspend fun fetchTickerCoins()

}
package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.toCoinChart
import com.filip.cryptoViewer.data.remote.dto.toCoinTicker
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository{
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }
    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.geyCoinById(coinId)
    }
    override suspend fun getTickerCoins(): List<CoinTickerItem> {
        return api.getTickerCoins().map { it.toCoinTicker() }
    }

    override suspend fun getTickerCoinById(coinId: String): CoinTickerItem {
        return api.getTickerCoinByID(coinId).toCoinTicker()
    }

    override suspend fun getChartCoinById(coinId: String, date: String): List<CoinChart> {
        return api.getChartCoin(coinId,date).map { it.toCoinChart() }
    }


}
package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.toCoinTicker
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository{
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getTickerCoins(): List<CoinTickerItem> {
        return api.getTickerCoins().map { it.toCoinTicker() }
    }

    override suspend fun getCoinById(coindId: String): CoinDetailDto {
        return api.geyCoinById(coindId)
    }

}
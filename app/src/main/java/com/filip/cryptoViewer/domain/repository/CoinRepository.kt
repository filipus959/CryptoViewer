package com.filip.cryptoViewer.domain.repository

import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.domain.model.CoinTickerItem

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getTickerCoins(): List<CoinTickerItem>
    suspend fun getCoinById(coindId: String): CoinDetailDto
}
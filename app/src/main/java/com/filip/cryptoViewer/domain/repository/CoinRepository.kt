package com.filip.cryptoViewer.domain.repository

import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerItem

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getTickerCoins(): ArrayList<CoinTickerItem>
    suspend fun getCoinById(coindId: String): CoinDetailDto
}
package com.filip.cryptoViewer.data.remote

import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerItem
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>
    @GET("/tickers")
    suspend fun getTickerCoins(): ArrayList<CoinTickerItem>
    @GET("/v1/coins/{coindId}")
    suspend fun geyCoinById(@Path("coindId") coindId: String): CoinDetailDto
}
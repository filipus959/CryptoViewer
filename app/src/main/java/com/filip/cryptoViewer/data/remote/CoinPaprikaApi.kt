package com.filip.cryptoViewer.data.remote

import com.filip.cryptoViewer.data.remote.dto.CoinChartDto
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.CoinExchangeDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinPaprikaApi {
    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto

    @GET("/v1/tickers")
    suspend fun getTickerCoins(): ArrayList<CoinTickerDto>

    @GET("/v1/tickers/{coinId}")
    suspend fun getTickerCoinByID(@Path("coinId") coinId: String): CoinTickerDto

    @GET("/v1/tickers/{coinId}/historical")
    suspend fun getChartCoin(
        @Path("coinId") coinId: String,
        @Query("start") date: String,
        @Query("interval") interval: String = "1d",
    ): ArrayList<CoinChartDto>

    @GET("/v1/price-converter")
    suspend fun getCoinExchange(
        @Query("base_currency_id") coinId: String,
        @Query("quote_currency_id") coinId2: String,
        @Query("amount") amount: Int,
    ): CoinExchangeDto
}

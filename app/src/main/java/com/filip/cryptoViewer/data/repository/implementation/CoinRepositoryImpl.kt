package com.filip.cryptoViewer.data.repository.implementation

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinExchange
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val coinTickerItemDao: CoinTickerItemDao,
    private val coinDetailDao: CoinDetailDao,
    private val coinChartDao: CoinChartDao,
    private val coinExchangeDao: CoinExchangeDao

) : CoinRepository {
    private val currentDateMinus364Days: LocalDate = LocalDate.now().minusDays(364)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val formattedDate: String = currentDateMinus364Days.format(formatter)

    override suspend fun fetchTickerCoins() {
        try {
            // Fetch data from API and cache it in the local database
            val tickerCoins = api.getTickerCoins().map { it.toDbModel() }
            coinTickerItemDao.insertAllCoinTickerItems(tickerCoins)

        } catch (e: HttpException) {
            println("Error: ${e.localizedMessage ?: "Unexpected Error"}")

        } catch (e: IOException) {
            handleNoInternet()
        }
    }

    private suspend fun handleNoInternet() {
        val cachedData = coinTickerItemDao.getAllCoinTickerItems()
            .map { it.map { dbItem -> dbItem.toDomainModel() } }
            .firstOrNull()

        if (cachedData.isNullOrEmpty()) {
            println("Couldn't reach server and no cached data available")
        }
    }



    override suspend fun getCoinExchanges(coinId: String, coinId2: String, amount: Int): CoinExchange {
        return try {
            val coinExchange = api.getCoinExchange(coinId, coinId2,amount).toDbModel()
            coinExchangeDao.insertCoinExchange(coinExchange)

            coinExchangeDao.getCoinExchange(coinId,coinId2,amount)?.toDomainModel()
               ?: throw IllegalStateException("Coin exchange not found after insertion")
        } catch (e: IOException) {
            val cachedExchange = coinExchangeDao.getCoinExchange(coinId,coinId2,amount)
                ?: throw RuntimeException("No internet and no cached data available", e)
            cachedExchange.toDomainModel()
        }
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        return try {
            val coinDetail = api.getCoinById(coinId).toDbModel()
            coinDetailDao.insertCoinDetail(coinDetail)

            coinDetailDao.getCoinDetailById(coinId)?.toDomainModel()
                ?: throw IllegalStateException("Coin detail not found after insertion")

        } catch (e: IOException) {
            val cachedDetail = coinDetailDao.getCoinDetailById(coinId)
                ?: throw RuntimeException("No internet and no cached data available", e)
            cachedDetail.toDomainModel()

        } catch (e: Exception) {
            throw RuntimeException("Failed to get coin detail", e)
        }
    }

    override suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>> {
        return coinTickerItemDao.getAllCoinTickerItems()
            .map { it.map { dbItem -> dbItem.toDomainModel() } }
    }

    override suspend fun getChartCoinById(coinId: String): List<CoinChart> {
        return try {
            // Fetch data from API
            val chartDataResponse = api.getChartCoin(coinId, formattedDate)

            // Check if the API response is null or empty
            if (chartDataResponse.isEmpty()) {
                throw RuntimeException("Received null or empty data from API")
            }

            // Map to DB models
            val chartData = chartDataResponse.map { it.toDbModel(coinId) }
            coinChartDao.insertAllCoinCharts(chartData)

            // Fetch from DAO
            val cachedData = coinChartDao.getCoinChartById(coinId)
            if (cachedData.isEmpty()) {
                throw RuntimeException("No data found in the database for coinId: $coinId")
            }

            cachedData.map { it.toDomainModel(coinId) }

        } catch (e: IOException) {
            // Handle IO exceptions
            val cachedData = coinChartDao.getCoinChartById(coinId)
            if (cachedData.isNotEmpty()) {
                cachedData.map { it.toDomainModel(coinId) }
            } else {
                throw RuntimeException("Couldn't reach server and no cached data available", e)
            }

        } catch (e: Exception) {
            throw RuntimeException("Failed to get coin chart", e)
        }
    }
}
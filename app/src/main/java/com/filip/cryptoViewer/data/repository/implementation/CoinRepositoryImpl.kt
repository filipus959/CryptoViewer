package com.filip.cryptoViewer.data.repository.implementation

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.domain.model.Coin
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val coinTickerItemDao: CoinTickerItemDao,
    private val coinDetailDao: CoinDetailDao,
    private val coinChartDao: CoinChartDao
) : CoinRepository {

    // Define the function without returning a value
    override suspend fun fetchTickerCoins() {
        // Emit loading state and perform API operations without returning a flow
        try {
            // Fetch data from the API
            val tickerCoinsApiResponse = api.getTickerCoins()

            // Cache the fetched data in the local database
            coinTickerItemDao.insertAllCoinTickerItems(
                tickerCoinsApiResponse.map { apiEntry -> apiEntry.toDbModel() }
            )

        } catch (e: HttpException) {
            // Handle HTTP exceptions (log or handle error state if needed)
            println("Error: ${e.localizedMessage ?: "Unexpected Error"}")

        } catch (e: IOException) {
            // Handle IO exceptions (e.g., no internet connection)
            // Fetch data from the local database as a fallback
            coinTickerItemDao.getAllCoinTickerItems()
                .map { dbList -> dbList.map { it.toDomainModel() } }
                .collect { cachedData ->
                    if (cachedData.isEmpty()) {
                        println("Couldn't reach server and no cached data available")
                    }
                    // Optionally handle offline cached data
                }
        }
    }

    override suspend fun getCoins(): List<Coin> {
        return api.getCoins().map { it.toCoin() }
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        return try {
            // Attempt to fetch the data from the API
            val coinByIdApiResponse = api.getCoinById(coinId)

            // Insert the fetched data into the database
            coinDetailDao.insertCoinDetail(coinByIdApiResponse.toDbModel())

            // After fetching, get the updated data from the database and return it
            val coinDetailEntity = coinDetailDao.getCoinDetailById(coinId)

            coinDetailEntity?.toDomainModel()
                ?: throw IllegalStateException("Expected coin detail to be available after insertion")

        } catch (e: IOException) {
            // Handle no internet connection by falling back to the local cache
            val cachedCoinDetailEntity = coinDetailDao.getCoinDetailById(coinId)

            cachedCoinDetailEntity?.toDomainModel() ?: throw RuntimeException(
                "No internet connection and no cached data available", e
            )

        } catch (e: Exception) {
            // Handle any other errors
            throw RuntimeException("Failed to get coin detail", e)
        }
    }



    override suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>> {
        return coinTickerItemDao.getAllCoinTickerItems()
            .map { dbModels ->
                dbModels.map { item -> item.toDomainModel() }
            }
    }

    override suspend fun getChartCoinById(coinId: String, date: String): List<CoinChart> {
        return try {
            // Try to fetch data from the API
            val coinChartByIdApiResponse = api.getChartCoin(coinId, date)
            // Insert the fetched data into the database
            coinChartDao.insertAllCoinCharts(coinChartByIdApiResponse.map { it.toDbModel(coinId) })

            // After successful fetch, return the updated data from the database
            val coinChartEntities = coinChartDao.getCoinChartById(coinId)
            coinChartEntities.map { item -> item.toDomainModel(coinId) }

        } catch (e: IOException) {
            // Handle the case when there's no internet connection
            // Fallback to the cached data in the database
            val cachedCoinChartEntities = coinChartDao.getCoinChartById(coinId)
            if (cachedCoinChartEntities.isNotEmpty()) {
                cachedCoinChartEntities.map { item -> item.toDomainModel(coinId) }
            } else {
                // Throw an error if no cached data is available
                throw RuntimeException("Couldn't reach server and no cached data available", e)
            }

        } catch (e: Exception) {
            // Handle other exceptions
            throw RuntimeException("Failed to get coin detail", e)
        }
    }
}
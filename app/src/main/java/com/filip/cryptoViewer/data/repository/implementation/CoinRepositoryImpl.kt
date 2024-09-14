package com.filip.cryptoViewer.data.repository.implementation

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.data.remote.dto.toCoinChart
import com.filip.cryptoViewer.domain.model.Coin
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val coinTickerItemDao: CoinTickerItemDao,
    private val coinDetailDao: CoinDetailDao,
    private val coinChartDao: CoinChartDao
) : CoinRepository {

    override suspend fun fetchTickerCoins() {
        val tickerCoinsApiResponse = api.getTickerCoins()

//
//        flow {
//            try {
//                emit(Resource.Loading())
//                val coins = repository.observeTickerCoins()
//                emit(Resource.Success(coins))
//            } catch(e: HttpException) {
//                emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
//            } catch(e: IOException) {
//                emit(Resource.Error("Couldn't reach server. Please check your server connection"))
//            }
//        }

        coinTickerItemDao.insertAllCoinTickerItems(
            tickerCoinsApiResponse
                .map { apiEntry -> apiEntry.toDbModel() }
        )
    }

    override suspend fun getCoins(): List<Coin> {
        return api.getCoins().map { it.toCoin() }
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        // Fetch data from the API
        val coinByIdApiResponse = api.getCoinById(coinId)

        // Insert the fetched data into the database
        coinDetailDao.insertCoinDetail(coinByIdApiResponse.toDbModel())

        return try {
            // Try to get the coin detail from the database
            val coinDetailEntity = coinDetailDao.getCoinDetailById(coinId)

            // Check if the result is null
            // Convert and return the non-null entity
            coinDetailEntity?.toDomainModel() ?: // Handle the case where no data is found
                throw IllegalStateException("Expected coin detail to be available after insertion")
        } catch (e: Exception) {
            // Handle exceptions appropriately
            // For example, you can rethrow the exception or handle it based on your needs
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
        return api.getChartCoin(coinId, date).map { it.toCoinChart() }
    }
}
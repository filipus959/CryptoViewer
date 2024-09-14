package com.filip.cryptoViewer.data.repository.implementation

import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.data.remote.dto.toCoinChart
import com.filip.cryptoViewer.data.remote.dto.toCoinDetal
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
    private val coinTickerItemDao: CoinTickerItemDao
) : CoinRepository {

    override suspend fun fetchTickerCoins() {
        val tickerCoinsApiResponse = api.getTickerCoins()

//  if ever to use resource, use it here
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
        return api.geyCoinById(coinId).toCoinDetal()
    }

    override suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>> {
        return coinTickerItemDao.getAllCoinTickerItems()
            .map { dbModels ->
                dbModels.map { item -> item.toDomainModel() }
            }
    }

//    override suspend fun getTickerCoinById(coinId: String): CoinTickerItem {
//        return api.getTickerCoinByID(coinId).toCoinTicker()
//    }

    override suspend fun getChartCoinById(coinId: String, date: String): List<CoinChart> {
        return api.getChartCoin(coinId, date).map { it.toCoinChart() }
    }
}
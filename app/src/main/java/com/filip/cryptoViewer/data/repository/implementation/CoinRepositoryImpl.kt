package com.filip.cryptoViewer.data.repository.implementation

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.util.NetworkBoundResource
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinExchange
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val formatterPattern = "yyyy-MM-dd"

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi,
    private val coinTickerItemDao: CoinTickerItemDao,
    private val coinDetailDao: CoinDetailDao,
    private val coinChartDao: CoinChartDao,
    private val coinExchangeDao: CoinExchangeDao,
) : CoinRepository {

    private val currentDateMinus364Days: LocalDate = LocalDate.now().minusDays(364)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(formatterPattern)
    private val formattedDate: String = currentDateMinus364Days.format(formatter)

    override suspend fun fetchTickerCoins() {
        NetworkBoundResource(
            fetchFromLocal = { coinTickerItemDao.getAllCoinTickerItems().firstOrNull() },
            fetchFromRemote = { api.getTickerCoins().map { it.toDbModel() } },
            saveRemoteResult = { coinTickerItemDao.insertAllCoinTickerItems(it) },
        ).fetch()
    }

    override suspend fun getCoinExchanges(
        coinId: String,
        coinId2: String,
        amount: Int,
    ): CoinExchange {
        return NetworkBoundResource(
            fetchFromLocal = {
                coinExchangeDao.getCoinExchange(
                    coinId = coinId,
                    coinId2 = coinId2,
                    amount = amount,
                )
            },
            fetchFromRemote = {
                api.getCoinExchange(
                    coinId = coinId,
                    coinId2 = coinId2,
                    amount = amount,
                ).toDbModel()
            },
            saveRemoteResult = { coinExchangeDao.insertCoinExchange(it) },
        ).fetch().toDomainModel()
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        return NetworkBoundResource(
            fetchFromLocal = { coinDetailDao.getCoinDetailById(coinId = coinId) },
            fetchFromRemote = { api.getCoinById(coinId = coinId).toDbModel() },
            saveRemoteResult = { coinDetailDao.insertCoinDetail(it) },
        ).fetch().toDomainModel()
    }

    override fun observeTickerCoins(): Flow<List<CoinTickerItem>> {
        return coinTickerItemDao.getAllCoinTickerItems()
            .map { it.map { dbItem -> dbItem.toDomainModel() } }
    }

    override suspend fun getChartCoinById(coinId: String): List<CoinChart> {
        return NetworkBoundResource(
            fetchFromLocal = { coinChartDao.getCoinChartById(coinId) },
            fetchFromRemote = {
                api.getChartCoin(coinId, formattedDate).map { it.toDbModel(coinId) }
            },
            saveRemoteResult = { coinChartDao.insertAllCoinCharts(it) },
        ).fetch().map { it.toDomainModel(coinId) }
    }
}

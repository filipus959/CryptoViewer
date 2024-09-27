package com.filip.cryptoViewer.presentation.ui.screens.preview

import com.filip.cryptoViewer.domain.model.Coin
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.model.CoinExchange
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCoinRepository : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        TODO("Not yet implemented")
    }

    override suspend fun getCoinById(coinId: String): CoinDetail {
        TODO("Not yet implemented")
    }

    override suspend fun observeTickerCoins(): Flow<List<CoinTickerItem>> {
        return flowOf(
            listOf(
                CoinTickerItem(id = "bitcoin", name = "Bitcoin", rank = 1, symbol = "BTC", percent_change_24h = 1.5, usdPrice = 30000.0),
                CoinTickerItem(id = "ethereum", name = "Ethereum", rank = 2, symbol = "ETH", percent_change_24h = -0.8, usdPrice = 2000.0),
                CoinTickerItem(id = "litecoin", name = "Litecoin", rank = 3, symbol = "LTC", percent_change_24h = 0.3, usdPrice = 150.0)
            )
        )
    }

    override suspend fun getChartCoinById(coinId: String): List<CoinChart> {
        TODO("Not yet implemented")
    }

    override suspend fun getCoinExchanges(coinId: String, coinId2: String): CoinExchange {
        return CoinExchange(price = 0.0667,coinId,coinId2)
    }

    override suspend fun fetchTickerCoins() {
        TODO("Not yet implemented")
    }
}

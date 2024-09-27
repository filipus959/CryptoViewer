package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.entity.CoinExchangeEntity

class CoinExchangeLocalRepository(private val coinExchangeDao : CoinExchangeDao) {
    suspend fun insertCoinExchange(coinExchangeEntity: CoinExchangeEntity) = coinExchangeDao.insertCoinExchange(coinExchangeEntity)
    suspend fun getCoinExchanges(coinId: String, coinId2: String): CoinExchangeEntity? = coinExchangeDao.getCoinExchange(coinId,coinId2)
}
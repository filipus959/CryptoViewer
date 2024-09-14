package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.local.dao.CoinDao
import com.filip.cryptoViewer.data.local.entity.CoinEntity

class CoinLocalRepository(private val coinDao: CoinDao) {

    suspend fun insertCoin(coinEntity: CoinEntity) {
        coinDao.insertCoin(coinEntity)
    }

    suspend fun getAllCoins(): List<CoinEntity> {
        return coinDao.getAllCoins()
    }

    suspend fun getCoinById(coinId: String): CoinEntity? {
        return coinDao.getCoinById(coinId)
    }

    suspend fun deleteAllCoins() {
        coinDao.deleteAllCoins()
    }
    suspend fun insertAllCoins(coins: List<CoinEntity>) {
        coinDao.insertAllCoins(coins)
    }
}
package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity

class CoinDetailLocal(private val coinDetailDao: CoinDetailDao) {

    suspend fun insertCoinDetail(coinDetailEntity: CoinDetailEntity) {
        coinDetailDao.insertCoinDetail(coinDetailEntity)
    }

    suspend fun getCoinDetailById(coinId: String): CoinDetailEntity? {
        return coinDetailDao.getCoinDetailById(coinId)
    }
    suspend fun deleteCoinDetailById(coinId: String) {
        coinDetailDao.deleteCoinDetailById(coinId)
    }
    suspend fun updateCoinDetail(coinDetailEntity: CoinDetailEntity) {
        coinDetailDao.updateCoinDetail(coinDetailEntity)
    }
    suspend fun getAllCoinDetails(): List<CoinDetailEntity> {
        return coinDetailDao.getAllCoinDetails()
    }
    suspend fun deleteAllCoinDetails() {
        coinDetailDao.deleteAllCoinDetails()
    }
}
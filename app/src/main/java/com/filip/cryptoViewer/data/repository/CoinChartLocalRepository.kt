package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.entity.CoinChartEntity

class CoinChartLocalRepository(private val coinChartDao: CoinChartDao) {

    suspend fun insertCoinChart(coinChartEntity: CoinChartEntity) {
        coinChartDao.insertCoinChart(coinChartEntity)
    }

    suspend fun getAllCoinCharts(): List<CoinChartEntity> {
        return coinChartDao.getAllCoinCharts()
    }

    suspend fun getCoinChartById(coinId: String): List<CoinChartEntity> {
        return coinChartDao.getCoinChartById(coinId)
    }

    suspend fun deleteAllCoinCharts() {
        coinChartDao.deleteAllCoinCharts()
    }
}
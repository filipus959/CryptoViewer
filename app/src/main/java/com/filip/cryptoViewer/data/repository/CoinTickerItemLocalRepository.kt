package com.filip.cryptoViewer.data.repository

import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity

class CoinTickerItemLocalRepository(private val coinTickerItemDao: CoinTickerItemDao) {

    suspend fun insertCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity) {
        coinTickerItemDao.insertCoinTickerItem(coinTickerItemEntity)
    }
    suspend fun updateCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity) {
        coinTickerItemDao.updateCoinTickerItem(coinTickerItemEntity)
    }

    suspend fun insertAllCoinTickerItems(coinTickerItems: List<CoinTickerItemEntity>) {
        coinTickerItemDao.insertAllCoinTickerItems(coinTickerItems)
    }

    // not needed repeat with others, into repo
//    suspend fun getAllCoinTickerItems(): List<CoinTickerItemEntity> {
//        return coinTickerItemDao.getAllCoinTickerItems()
//    }

    suspend fun getCoinTickerItemById(tickerId: String): CoinTickerItemEntity? {
        return coinTickerItemDao.getCoinTickerItemById(tickerId)
    }

    suspend fun deleteAllCoinTickerItems() {
        coinTickerItemDao.deleteAllCoinTickerItems()
    }
}
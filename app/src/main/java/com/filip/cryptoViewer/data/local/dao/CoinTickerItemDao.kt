package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinTickerItemDao {

    // Insert a new coin chart data record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity)

    // Insert multiple coin chart data records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinTickerItems(coinTickerItems: List<CoinTickerItemEntity>)

    // Update coin chart data
    @Upsert
    suspend fun updateCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity)

    // Query to get all coin chart data
    @Query("SELECT * FROM coin_ticker_items ORDER BY rank ASC")
    fun getAllCoinTickerItems(): Flow<List<CoinTickerItemEntity>>

    // Query to get coin chart data by coin ID
    @Query("SELECT * FROM coin_ticker_items WHERE id = :coinId")
    suspend fun getCoinTickerItemById(coinId: String): CoinTickerItemEntity?

    // Query to delete all coin chart data
    @Query("DELETE FROM coin_ticker_items")
    suspend fun deleteAllCoinTickerItems()

    // Query to delete coin chart data by coin ID
    @Query("DELETE FROM coin_ticker_items WHERE id = :coinId")
    suspend fun deleteCoinTickerItemById(coinId: String)
}
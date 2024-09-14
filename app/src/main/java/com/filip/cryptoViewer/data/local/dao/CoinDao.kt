package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinEntity

@Dao
interface CoinDao {

    // Insert a new coin chart data record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinEntity)

    // Insert multiple coin chart data records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coins: List<CoinEntity>)

    // Update coin chart data
    @Upsert
    suspend fun updateCoin(coinEntity: CoinEntity)

    // Query to get all coin chart data
    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): List<CoinEntity>

    // Query to get coin chart data by coin ID
    @Query("SELECT * FROM coins WHERE id = :coinId")
    suspend fun getCoinById(coinId: String): CoinEntity?

    // Query to delete all coin chart data
    @Query("DELETE FROM coins")
    suspend fun deleteAllCoins()

    // Query to delete coin chart data by coin ID
    @Query("DELETE FROM coins WHERE id = :coinId")
    suspend fun deleteCoinChartById(coinId: String)
}
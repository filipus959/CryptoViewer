package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity

@Dao
interface CoinDetailDao {

    // Insert a new coin chart data record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetail(coinDetailEntity: CoinDetailEntity)

    // Insert multiple coin chart data records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinDetails(coinDetails: List<CoinDetailEntity>)

    // Update coin chart data
    @Upsert
    suspend fun updateCoinDetail(coinDetailEntity: CoinDetailEntity)

    // Query to get all coin chart data
    @Query("SELECT * FROM coin_details")
    suspend fun getAllCoinDetails(): List<CoinDetailEntity>

    // Query to get coin chart data by coin ID
    @Query("SELECT * FROM coin_details WHERE coinId = :coinId")
    suspend fun getCoinDetailById(coinId: String): CoinDetailEntity?

    // Query to delete all coin chart data
    @Query("DELETE FROM coin_details")
    suspend fun deleteAllCoinDetails()

    // Query to delete coin chart data by coin ID
    @Query("DELETE FROM coin_details WHERE coinId = :coinId")
    suspend fun deleteCoinDetailById(coinId: String)
}

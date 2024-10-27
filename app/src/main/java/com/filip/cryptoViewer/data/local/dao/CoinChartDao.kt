package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinChartEntity

@Dao
interface CoinChartDao {

    // Insert a new coin chart data record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinChart(coinChartEntity: CoinChartEntity)

    // Insert multiple coin chart data records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinCharts(coinCharts: List<CoinChartEntity>)

    // Update coin chart data
    @Upsert
    suspend fun updateCoinChart(coinChartEntity: CoinChartEntity)

    // Query to get all coin chart data
    @Query("SELECT * FROM coin_charts")
    suspend fun getAllCoinCharts(): List<CoinChartEntity>

    // Query to get coin chart data by coin ID
    @Query("SELECT * FROM coin_charts WHERE coinId = :coinId")
    suspend fun getCoinChartById(coinId: String): List<CoinChartEntity>

    // Query to delete all coin chart data
    @Query("DELETE FROM coin_charts")
    suspend fun deleteAllCoinCharts()

    // Query to delete coin chart data by coin ID
    @Query("DELETE FROM coin_charts WHERE coinId = :coinId")
    suspend fun deleteCoinChartById(coinId: String)
}

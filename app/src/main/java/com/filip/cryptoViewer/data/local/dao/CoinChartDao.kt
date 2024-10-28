package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinChartEntity

@Dao
interface CoinChartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinChart(coinChartEntity: CoinChartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinCharts(coinCharts: List<CoinChartEntity>)

    @Upsert
    suspend fun updateCoinChart(coinChartEntity: CoinChartEntity)

    @Query("SELECT * FROM coin_charts WHERE coinId = :coinId")
    suspend fun getCoinChartById(coinId: String): List<CoinChartEntity>

    @Query("DELETE FROM coin_charts")
    suspend fun deleteAllCoinCharts()

    @Query("DELETE FROM coin_charts WHERE coinId = :coinId")
    suspend fun deleteCoinChartById(coinId: String)
}

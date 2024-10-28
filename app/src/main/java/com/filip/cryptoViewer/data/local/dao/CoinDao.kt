package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinEntity

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coins: List<CoinEntity>)

    @Upsert
    suspend fun updateCoin(coinEntity: CoinEntity)

    @Query("SELECT * FROM coins WHERE id = :coinId")
    suspend fun getCoinById(coinId: String): CoinEntity?

    @Query("DELETE FROM coins")
    suspend fun deleteAllCoins()

    @Query("DELETE FROM coins WHERE id = :coinId")
    suspend fun deleteCoinChartById(coinId: String)
}

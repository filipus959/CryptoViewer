package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity

@Dao
interface CoinDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinDetail(coinDetailEntity: CoinDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinDetails(coinDetails: List<CoinDetailEntity>)

    @Upsert
    suspend fun updateCoinDetail(coinDetailEntity: CoinDetailEntity)

    @Query("SELECT * FROM coin_details WHERE coinId = :coinId")
    suspend fun getCoinDetailById(coinId: String): CoinDetailEntity?

    @Query("DELETE FROM coin_details")
    suspend fun deleteAllCoinDetails()

    @Query("DELETE FROM coin_details WHERE coinId = :coinId")
    suspend fun deleteCoinDetailById(coinId: String)
}

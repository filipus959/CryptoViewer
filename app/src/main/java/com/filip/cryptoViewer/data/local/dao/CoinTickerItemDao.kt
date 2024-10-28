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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoinTickerItems(coinTickerItems: List<CoinTickerItemEntity>)

    @Upsert
    suspend fun updateCoinTickerItem(coinTickerItemEntity: CoinTickerItemEntity)

    @Query("SELECT * FROM coin_ticker_items ORDER BY rank ASC")
    fun getAllCoinTickerItems(): Flow<List<CoinTickerItemEntity>>

    @Query("SELECT * FROM coin_ticker_items WHERE id = :coinId")
    suspend fun getCoinTickerItemById(coinId: String): CoinTickerItemEntity?

    @Query("DELETE FROM coin_ticker_items")
    suspend fun deleteAllCoinTickerItems()

    @Query("DELETE FROM coin_ticker_items WHERE id = :coinId")
    suspend fun deleteCoinTickerItemById(coinId: String)
}

package com.filip.cryptoViewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.filip.cryptoViewer.data.local.entity.CoinExchangeEntity

@Dao
interface CoinExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinExchange(coinExchange: CoinExchangeEntity)

    @Query("SELECT * FROM coin_exchanges WHERE coinId = :coinId AND coinId2 = :coinId2 AND amount = :amount")
    suspend fun getCoinExchange(coinId: String, coinId2: String, amount: Int): CoinExchangeEntity?
}

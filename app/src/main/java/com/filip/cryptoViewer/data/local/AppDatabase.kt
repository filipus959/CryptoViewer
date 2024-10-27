package com.filip.cryptoViewer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.filip.cryptoViewer.data.local.converter.Converters
import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.entity.CoinChartEntity
import com.filip.cryptoViewer.data.local.entity.CoinDetailEntity
import com.filip.cryptoViewer.data.local.entity.CoinEntity
import com.filip.cryptoViewer.data.local.entity.CoinExchangeEntity
import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity

@Database(
    entities = [CoinEntity::class, CoinDetailEntity::class, CoinChartEntity::class, CoinTickerItemEntity::class, CoinExchangeEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
    abstract fun coinDetailDao(): CoinDetailDao
    abstract fun coinChartDao(): CoinChartDao
    abstract fun coinTickerItemDao(): CoinTickerItemDao
    abstract fun coinExchangeDao(): CoinExchangeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database",
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

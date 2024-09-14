package com.filip.cryptoViewer.di

import android.content.Context
import com.filip.cryptoViewer.data.local.AppDatabase
import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.repository.implementation.CoinRepositoryImpl
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCoinDao(appDatabase: AppDatabase): CoinDao {
        return appDatabase.coinDao()
    }

    @Provides
    @Singleton
    fun provideCoinDetailDao(appDatabase: AppDatabase): CoinDetailDao {
        return appDatabase.coinDetailDao()
    }

    @Provides
    @Singleton
    fun provideCoinChartDao(appDatabase: AppDatabase): CoinChartDao {
        return appDatabase.coinChartDao()
    }
    @Provides
    @Singleton
    fun provideCoinTickerItemDao(appDatabase: AppDatabase): CoinTickerItemDao {
        return appDatabase.coinTickerItemDao()
    }

    @Provides
    @Singleton
    fun provideCoinRepository(coinService: CoinPaprikaApi, coinTickerItemDao: CoinTickerItemDao): CoinRepository {
        return CoinRepositoryImpl(coinService, coinTickerItemDao)
    }
}
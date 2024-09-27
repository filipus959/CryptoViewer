package com.filip.cryptoViewer.di

import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.repository.implementation.CoinRepositoryImpl
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRepository(coinService: CoinPaprikaApi, coinTickerItemDao: CoinTickerItemDao, coinDetailDao: CoinDetailDao, coinChartDao: CoinChartDao, coinExchangeDao: CoinExchangeDao): CoinRepository {
        return CoinRepositoryImpl(coinService, coinTickerItemDao,coinDetailDao,coinChartDao,coinExchangeDao)
    }
}
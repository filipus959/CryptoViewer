//
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
////    @Provides
////    @Singleton
////    fun provideCoinLocalRepository(coinDao: CoinDao): CoinLocalRepository {
////        return CoinLocalRepository(coinDao)
////    }
//
////    @Provides
////    @Singleton
////    fun provideCoinRemoteRepository(coinService: CoinPaprikaApi, coinTickerItemDao: CoinTickerItemDao): CoinRepositoryImpl {
////        return CoinRepositoryImpl(coinService, coinTickerItemDao)
////    }
//
////    @Provides
////    @Singleton
////    fun provideCoinRepository(
////        coinLocalRepository: CoinLocalRepository,
////        coinRemoteRepository: CoinRepository
////    ): CoinRepository {
////        return CoinRepositoryImpl(coinLocalRepository, coinRemoteRepository)
////    }
//}
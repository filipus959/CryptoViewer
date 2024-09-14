package com.filip.cryptoViewer.domain.use_case.observe_Ticker_Coins

import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTickerCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){
    suspend operator fun invoke(): Flow<List<CoinTickerItem>> =
        repository.observeTickerCoins()
}

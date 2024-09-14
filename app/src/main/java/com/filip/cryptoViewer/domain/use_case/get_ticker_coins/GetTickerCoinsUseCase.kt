package com.filip.cryptoViewer.domain.use_case.get_ticker_coins

import com.filip.cryptoViewer.domain.repository.CoinRepository
import javax.inject.Inject

class GetTickerCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){
    suspend operator fun invoke() {
        repository.fetchTickerCoins()
    }
}
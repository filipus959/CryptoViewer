package com.filip.cryptoViewer.domain.use_case.get_Ticker_Coins

import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTickerCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){
    operator fun invoke(): Flow<Resource<List<CoinTickerItem>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getTickerCoins()
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Please check your server connection"))
        }
    }
}

package com.filip.cryptoViewer.domain.use_case.get_coins

import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.domain.model.Coin
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Please check your server connection"))
        }
    }
}
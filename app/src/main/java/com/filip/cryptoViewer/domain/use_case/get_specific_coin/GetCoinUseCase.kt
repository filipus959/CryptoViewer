package com.filip.cryptoViewer.domain.use_case.get_specific_coin

import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.data.remote.dto.toCoinDetal
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
){
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetal()
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Please check your server connection"))
        }
    }
}
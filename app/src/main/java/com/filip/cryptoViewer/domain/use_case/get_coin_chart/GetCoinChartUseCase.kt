package com.filip.cryptoViewer.domain.use_case.get_coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCoinChartUseCase @Inject constructor(
    private val repository: CoinRepository
){
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateMinus364Days: LocalDate = LocalDate.now().minusDays(364)
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val formattedDate = currentDateMinus364Days.format(formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(coinId: String): Flow<Resource<List<CoinChart>>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getChartCoinById(coinId,formattedDate.toString())
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Please check your server connection"))
        }
    }
}
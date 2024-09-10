package com.filip.cryptoViewer.presentation.coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.domain.use_case.get_coin_chart.GetCoinChartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CoinChartViewModel @Inject constructor(
    private val getCoinChartUseCase: GetCoinChartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinChartState())
    val state: State<CoinChartState> = _state
    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoinChart(coinId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCoinChart(coinId: String) {
        getCoinChartUseCase(coinId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CoinChartState(coins = result.data,id = coinId, marketCap = result.data.first().market_cap.toString())
                }
                is Resource.Error -> {
                    _state.value = CoinChartState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinChartState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
fun formatNumberWithCommas(numberString: String): String {
    // Remove any existing non-digit characters
    val cleanedNumber = numberString.replace("[^\\d]".toRegex(), "")

    // Check if the cleaned number is empty or zero
    if (cleanedNumber.isEmpty()) return "0"

    // Reverse the cleaned number to simplify the insertion of commas
    val reversedNumber = cleanedNumber.reversed()
    val withCommasReversed = reversedNumber.chunked(3).joinToString(",")

    // Reverse it back to get the final formatted number
    return withCommasReversed.reversed()
}
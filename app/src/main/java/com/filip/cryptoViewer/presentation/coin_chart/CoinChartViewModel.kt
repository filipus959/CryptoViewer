package com.filip.cryptoViewer.presentation.coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CoinChartViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinChartState())
    val state: State<CoinChartState> = _state
    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoinChart(coinId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getCoinChart(coinId: String) {
        _state.value = CoinChartState(isLoading = true)  // Set loading state

        try {
            // Assuming this function returns a List<CoinChart> directly
            val coinData = coinRepository.getChartCoinById(coinId)

            if (coinData.isNotEmpty()) {
                _state.value = CoinChartState(
                    coins = coinData,
                    id = coinId,
                    marketCap = coinData.first().market_cap.toString()
                )
            } else {
                _state.value = CoinChartState(
                    error = "No data available"
                )
            }
        } catch (e: Exception) {
            // Handle any errors that occur
            _state.value = CoinChartState(
                error = e.message ?: "An unexpected error occurred"
            )
        }
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
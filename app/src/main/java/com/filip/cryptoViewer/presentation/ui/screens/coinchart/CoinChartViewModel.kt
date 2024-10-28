package com.filip.cryptoViewer.presentation.ui.screens.coinchart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.presentation.CoinChartScreen
import com.filip.cryptoViewer.presentation.ui.screens.coinchart.components.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CoinChartViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(CoinChartState.Empty)
        private set
    var chartList by mutableStateOf(emptyList<CoinChart>())
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.toRoute<CoinChartScreen>().coinId.let { coinId ->
                getCoinChart(coinId)
            }
        }
    }

    private suspend fun getCoinChart(coinId: String) {
        state = state.copy(isLoading = true)

        try {
            val coinData = coinRepository.getChartCoinById(coinId)
            chartList = coinData

            state = if (coinData.isNotEmpty()) {
                state.copy(
                    coins = chartList,
                    id = coinId,
                    marketCap = chartList.first().marketCap.toString(),
                    isLoading = false,
                )
            } else {
                state.copy(
                    error = "No data available",
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                error = e.message ?: "An unexpected error occurred",
            )
        }
    }

    fun changeChartRange(days: Int) {
        val modifiedList = chartList.takeLast(days).let { list ->
            when (days) {
                365 -> list.filterIndexed { index, _ -> index % 30 == 0 }
                30 -> list.filterIndexed { index, _ -> index % 4 == 0 }
                else -> list
            }
        }
        updateListState(modifiedList)
    }

    private fun updateListState(modifiedList: List<CoinChart>) {
        state = state.copy(
            coins = modifiedList,
            isLoading = false,
            error = "",
        )
    }

    fun getDataPoints(): List<DataPoint> {
        val coins = state.coins
        val points = coins?.map { coin ->
            val zonedDateTime =
                ZonedDateTime.parse(coin.timestamp, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            val timestamp = zonedDateTime.toEpochSecond().toFloat()
            DataPoint(
                y = coin.price.toFloat(),
                x = timestamp,
                xLabel = formatTimestampToMonthDay(coin.timestamp),
            )
        }
        return points ?: emptyList()
    }
}

fun formatTimestampToMonthDay(timestamp: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = ZonedDateTime.parse(timestamp, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("MM.dd")

        dateTime.minusDays(1).format(outputFormatter)
    } catch (e: DateTimeParseException) {
        "Invalid date"
    }
}

fun formatNumberWithCommas(numberString: String): String {
    val cleanedNumber = numberString.replace("[^\\d]".toRegex(), "")
    if (cleanedNumber.isEmpty()) return "0"
    val reversedNumber = cleanedNumber.reversed()
    val withCommasReversed = reversedNumber.chunked(3).joinToString(",")
    return withCommasReversed.reversed()
}

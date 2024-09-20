package com.filip.cryptoViewer.presentation.coin_chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CoinChartViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CoinChartState())
        private set

    private var showStamps by mutableStateOf(false)
    private var timestamps by mutableStateOf(emptyList<String>())
    private var _chartList by mutableStateOf(emptyList<CoinChart>())
    private val chartList: List<CoinChart> get() = _chartList


    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoinChart(coinId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getCoinChart(coinId: String) {
        state = CoinChartState(isLoading = true)  // Set loading state

        try {
            // Assuming this function returns a List<CoinChart> directly
            val coinData = coinRepository.getChartCoinById(coinId)
            _chartList = coinData

            state = if (coinData.isNotEmpty()) {
                state.copy(
                    coins = _chartList,
                    id = coinId,
                    marketCap = _chartList.first().market_cap.toString(),
                    isLoading = false
                )
            } else {
                state.copy(
                    error = "No data available"
                )
            }
        } catch (e: Exception) {
            // Handle any errors that occur
            state = state.copy(
                error = e.message ?: "An unexpected error occurred"
            )
        }
    }
    fun changeChartRange(days: Int) {
        showStamps = days != 365
        var modifiedList = chartList.takeLast(days)
        if (days == 30) {
        modifiedList = modifiedList.filterIndexed { index, _ -> index % 4 == 0 }
        }
        updateListState(modifiedList)
    }

    private fun updateListState(modifiedList: List<CoinChart>) {
        state = state.copy(
            coins = modifiedList,
            isLoading = false,
            error = ""
        )
    }

    fun getTimeStamps(): List<String> {
        val stamps = state.coins
        timestamps = stamps.map { coin ->
            formatTimestampToMonthDay(coin.timestamp)
        }
        return if(timestamps.size > 300)
            getNext12Months()
        else
            timestamps
    }




}



@RequiresApi(Build.VERSION_CODES.O)
fun getNext12Months(): List<String> {
    val currentDate = LocalDate.now()
    val months = mutableListOf<String>()

    var date = currentDate.minusMonths(11)  // Start from 11 months ago to include the current month in the list

    repeat(12) {
        months.add(date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()))
        date = date.plusMonths(1)
    }

    return months
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestampToMonthDay(timestamp: String): String {
    return try {
        // Define the input format (ISO 8601)
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME

        // Parse the timestamp to ZonedDateTime
        val dateTime = ZonedDateTime.parse(timestamp, inputFormatter)

        // Define the output format
        val outputFormatter = DateTimeFormatter.ofPattern("MM.dd")

        // Format the ZonedDateTime to the desired format
        dateTime.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        // Handle parsing errors (e.g., invalid timestamp format)
        "Invalid date"
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




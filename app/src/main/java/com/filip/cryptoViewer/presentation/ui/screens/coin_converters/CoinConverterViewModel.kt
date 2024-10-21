package com.filip.cryptoViewer.presentation.ui.screens.coin_converters

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinConverterViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
) : ViewModel() {

    var state by mutableStateOf(CoinConverterState.Empty)
    private set

    private var _allCoinsData by mutableStateOf(emptyList<CoinTickerItem>())
    private val allCoinsData: List<CoinTickerItem> get() = _allCoinsData
    var amount by mutableIntStateOf(1)

    var selectedCoin1 by mutableStateOf<CoinTickerItem?>(null)
    var selectedCoin2 by mutableStateOf<CoinTickerItem?>(null)

    var searchQuery by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            observeTickerCoins()
        }
    }

    private fun runExchangeRate() = viewModelScope.launch {
        getExchangeRate()
    }

    @SuppressLint("DefaultLocale")
    private suspend fun getExchangeRate() {
        updateState(isLoading = true)

        if (selectedCoin1 != null && selectedCoin2 != null) {
            try {
                val exchangePrice = coinRepository.getCoinExchanges(selectedCoin1!!.id, selectedCoin2!!.id, amount)
                val formattedResult = String.format("%.4f", exchangePrice.price)
                updateState(isLoading = false, coins = allCoinsData, error = "", result = formattedResult)
            } catch (e: Exception) {
                updateState(isLoading = false, error = "An error occurred: ${e.localizedMessage}")
            }
        } else {
            updateState(isLoading = false, coins = allCoinsData, result = "Please select two coins")
        }
    }

    private suspend fun observeTickerCoins() {
        updateState(isLoading = true)

        try {
            coinRepository.observeTickerCoins().collect { coins ->
                _allCoinsData = coins
                updateListState()
            }
        } catch (e: Exception) {
            updateState(isLoading = false, error = "An error occurred: ${e.localizedMessage}")
        }
    }

    private fun updateListState() {
        val filteredCoins = filterCoinList(searchQuery)
        updateState(isLoading = false, coins = filteredCoins)
    }

    private fun updateState(
        isLoading: Boolean = false,
        error: String = "",
        coins: List<CoinTickerItem>? = null,
        result: String = ""
    ) {
        state = state.copy(
            isLoading = isLoading,
            result = result,
            error = error,
            coins = coins ?: state.coins
        )
    }

    fun firstSelection(coin: CoinTickerItem) {
        selectedCoin1 = coin
        runExchangeRate()
    }

    fun secondSelection(coin: CoinTickerItem) {
        selectedCoin2 = coin
        runExchangeRate()
    }

    fun onSearchQueryUpdated(query: String) {
        searchQuery = query
        updateListState()
    }

    private fun filterCoinList(query: String): List<CoinTickerItem> =
        if (query.isBlank()) allCoinsData else allCoinsData.filter {
            it.name.contains(query, ignoreCase = true)
        }

    fun onAmountChange(newAmount: String) {
        amount = newAmount.toIntOrNull() ?: 1
        runExchangeRate()
    }
}

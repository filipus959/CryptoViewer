package com.filip.cryptoViewer.presentation.ui.screens.coin_converters

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
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
data class CoinConverterViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
) : ViewModel() {

    var state by mutableStateOf(CoinConverterState.Empty)


    private var _allCoinsData by mutableStateOf(emptyList<CoinTickerItem>())
    private val allCoinsData: List<CoinTickerItem> get() = _allCoinsData
    var result by mutableStateOf("result")
    var selectedCoin1 by mutableStateOf(CoinTickerItem(id = "",name = "select coin", rank = 0, symbol = "", percent_change_24h = 0.0, usdPrice = 0.0))
    var selectedCoin2 by mutableStateOf(CoinTickerItem(id = "",name = "select coin", rank = 0, symbol = "", percent_change_24h = 0.0, usdPrice = 0.0))
    var searchQuery by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            observeTickerCoins()
        }
    }

    fun runExchangeRate() = viewModelScope.launch {
        getExchangeRate()
    }

    @SuppressLint("DefaultLocale")
    private suspend fun getExchangeRate() {
            state = state.copy(isLoading = true, error = "")
            if (selectedCoin1.name != "select coin" && selectedCoin2.name != "select coin")
                try {
                    coinRepository.getCoinExchanges(selectedCoin1.id, selectedCoin2.id)
                        .let { exchangePrice ->
                            result = String.format("%.4f", exchangePrice.price)
                        }
                } catch (e: Exception) {
                    state = state.copy(
                        isLoading = false,
                        error = "An error occurred: ${e.localizedMessage}"
                    )
                }
            else
                result = "Please select two coins"

    }

    private suspend fun observeTickerCoins() {
        state = state.copy(isLoading = true, error = "")

        try {
            coinRepository.observeTickerCoins().collect { coins ->
                _allCoinsData = coins
                updateListState()
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                coins = allCoinsData,
                error = "An error occurred: ${e.localizedMessage}"
            )
        }
    }
    private fun updateListState() {
        state = state.copy(
            isLoading = false,
            error = "",
            coins = filterCoinList(searchQuery)
        )
    }
    fun firstSelection(coin: CoinTickerItem) {
        selectedCoin1 = coin
        result = "result"

    }
    fun secondSelection(coin: CoinTickerItem) {
        selectedCoin2 = coin
        result = "result"


    }

    fun onSearchQueryUpdated(query: String) {
        searchQuery = query
        updateListState()
    }
    private fun filterCoinList(query: String): List<CoinTickerItem> =
        if (query.isBlank()) allCoinsData else allCoinsData.filter { it.name.contains(query, ignoreCase = true) }


}


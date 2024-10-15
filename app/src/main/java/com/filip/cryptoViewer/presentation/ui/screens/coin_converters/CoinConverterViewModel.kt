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
data class CoinConverterViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
) : ViewModel() {

    var state by mutableStateOf(CoinConverterState.Empty)


    private var _allCoinsData by mutableStateOf(emptyList<CoinTickerItem>())
    private val allCoinsData: List<CoinTickerItem> get() = _allCoinsData
    var result by mutableStateOf("result")
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
        state = state.copy(isLoading = true, error = "")

        // Check if selectedCoin1 and selectedCoin2 are not null before proceeding
        if (selectedCoin1 != null && selectedCoin2 != null) {
            try {
                // Fetch the exchange rate using the coin IDs
                coinRepository.getCoinExchanges(selectedCoin1!!.id, selectedCoin2!!.id, amount)
                    .let { exchangePrice ->
                        result = String.format("%.4f", exchangePrice.price)
                        state = state.copy(
                            isLoading = false
                        )
                    }
            } catch (e: Exception) {
                // Handle any error that occurs during the API call
                state = state.copy(
                    isLoading = false,
                    error = "An error occurred: ${e.localizedMessage}"
                )
            }
        } else {
            // If either coin is not selected, show a message asking the user to select two coins
            result = "Please select two coins"
            state = state.copy(
                isLoading = false
            )
        }
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
            it.name.contains(
                query,
                ignoreCase = true
            )
        }

    fun onAmountChange(newAmount: String) {
        amount = newAmount.toIntOrNull() ?: 1
        runExchangeRate()
    }

}


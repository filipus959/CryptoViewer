package com.filip.cryptoViewer.presentation.coin_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.use_case.get_ticker_coins.GetTickerCoinsUseCase
import com.filip.cryptoViewer.domain.use_case.observe_Ticker_Coins.ObserveTickerCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getTickerCoinsUseCase: GetTickerCoinsUseCase,
    private val observeTickerCoinsUseCase: ObserveTickerCoinsUseCase
) : ViewModel() {
    private var _state by mutableStateOf(CoinListState())
        private set

    var state2 by mutableStateOf(CoinTickerListState.Empty)
        private set

    var searchQuery by mutableStateOf("")
        private set

    private var allCoinsData: List<CoinTickerItem> = emptyList()

    init {
        // getCoins()
        viewModelScope.launch {
            getTickerCoinsUseCase()
            observeTickerCoins()
        }
    }

//    private fun getCoins() {
//        getCoinsUseCase().onEach { result ->
//            when(result) {
//                is Resource.Success -> {
//                    _state.value= CoinListState(coins = result.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    _state.value = CoinListState(
//                        error = result.message ?: "An unexpected error occured"
//                    )
//                }
//                is Resource.Loading -> {
//                    _state.value = CoinListState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    private suspend fun observeTickerCoins() {
        state2 = state2.copy(isLoading = true, error = "")

        // Collect the flow from the use case
        try {
            observeTickerCoinsUseCase().collect { coins ->
                // Save the full data set to a local variable
                allCoinsData = coins

                // Filter the list based on the current search query
                state2 = state2.copy(
                    coins = filterCoinList(query = searchQuery),
                    isLoading = false,
                    error = ""
                )
            }
        } catch (e: Exception) {
            state2 = state2.copy(
                isLoading = false,
                error = "An error occurred: ${e.localizedMessage}"
            )
        }
    }

    // Function to update the search query and filter the list
    fun onSearchQueryUpdated(query: String) {
        searchQuery = query
        updateListModels(query = query)
    }

    private fun updateListModels(query: String) {
        state2 = state2.copy(
            coins = filterCoinList(query)
        )
    }

    // Function to filter the coin list based on the `name` field
    private fun filterCoinList(query: String): List<CoinTickerItem> {
        return if (query.isBlank()) {
            allCoinsData
        } else {
            allCoinsData.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}


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

enum class SortOrder {
    ASCENDING, DESCENDING
}

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getTickerCoinsUseCase: GetTickerCoinsUseCase,
    private val observeTickerCoinsUseCase: ObserveTickerCoinsUseCase
) : ViewModel() {

    var state by mutableStateOf(CoinTickerListState.Empty)
        private set

    var searchQuery by mutableStateOf("")
        private set

    private var _allCoinsData by mutableStateOf(emptyList<CoinTickerItem>())
    val allCoinsData: List<CoinTickerItem> get() = _allCoinsData

    private var sortByPriceOrder by mutableStateOf(SortOrder.ASCENDING)
    private var sortByRankOrder by mutableStateOf(SortOrder.ASCENDING)
    private var sortByChangeOrder by mutableStateOf(SortOrder.ASCENDING)

    val priceArrow: String get() = getArrow(sortByPriceOrder)
    val rankArrow: String get() = getArrow(sortByRankOrder)
    val changeArrow: String get() = getArrow(sortByChangeOrder)

    init {
        viewModelScope.launch {
            getTickerCoinsUseCase() // Trigger any initial data fetching
            observeTickerCoins()
        }
    }

    private fun getArrow(sortOrder: SortOrder) = if (sortOrder == SortOrder.ASCENDING) "↑" else "↓"

    fun sortCoinsByPrice() {
        sortByPriceOrder = if (sortByPriceOrder == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
        _allCoinsData = _allCoinsData.sortedWith(compareBy { it.usdPrice })
        if (sortByPriceOrder == SortOrder.DESCENDING) {
            _allCoinsData = _allCoinsData.reversed()
        }
        updateListState()
    }

    fun sortCoinsByRank() {
        sortByRankOrder = if (sortByRankOrder == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
        _allCoinsData = _allCoinsData.sortedWith(compareBy { it.rank })
        if (sortByRankOrder == SortOrder.DESCENDING) {
            _allCoinsData = _allCoinsData.reversed()
        }
        updateListState()
    }

    fun sortCoinsByChange() {
        sortByChangeOrder = if (sortByChangeOrder == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
        _allCoinsData = _allCoinsData.sortedWith(compareBy { it.percent_change_24h })
        if (sortByChangeOrder == SortOrder.DESCENDING) {
            _allCoinsData = _allCoinsData.reversed()
        }
        updateListState()
    }

    private suspend fun observeTickerCoins() {
        state = state.copy(isLoading = true, error = "")

        try {
            observeTickerCoinsUseCase().collect { coins ->
                _allCoinsData = coins
                updateListState()
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = "An error occurred: ${e.localizedMessage}"
            )
        }
    }

    fun onSearchQueryUpdated(query: String) {
        searchQuery = query
        updateListState()
    }

    private fun updateListState() {
        state = state.copy(
            coins = filterCoinList(searchQuery),
            isLoading = false,
            error = ""
        )
    }

    private fun filterCoinList(query: String): List<CoinTickerItem> =
        if (query.isBlank()) allCoinsData else allCoinsData.filter { it.name.contains(query, ignoreCase = true) }
}
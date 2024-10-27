package com.filip.cryptoViewer.presentation.ui.screens.cointickerlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.model.SortCriteria
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.model.SortOrder
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.domain.usecase.SortAndFilterCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinTickerListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val sortAndFilterCoinsUseCase: SortAndFilterCoinsUseCase,
) : ViewModel() {

    var state = MutableStateFlow(CoinTickerListState.Empty)
        private set

    var searchQuery by mutableStateOf("")
        private set
    private var sortCriteria by mutableStateOf(SortCriteria(SortField.RANK, SortOrder.DESCENDING))
    private var allCoinsData by mutableStateOf(emptyList<CoinTickerItem>())

    init {
        viewModelScope.launch {
            fetchTickerCoins()
            observeTickerCoins()
        }
    }

    fun getArrowForField(field: SortField): String {
        return if (sortCriteria.field == field) {
            if (sortCriteria.order == SortOrder.ASCENDING) "↑" else "↓"
        } else {
            ""
        }
    }

    fun updateSortCriteria(field: SortField) {
        sortCriteria = if (sortCriteria.field == field) {
            sortCriteria.copy(order = if (sortCriteria.order == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING)
        } else {
            SortCriteria(field = field, order = SortOrder.ASCENDING)
        }
        sortAndFilterCoins()
    }

    private fun sortAndFilterCoins() {
        val filteredList = sortAndFilterCoinsUseCase.filterCoinList(searchQuery, allCoinsData)
        val sortedList = sortAndFilterCoinsUseCase.sortCoins(filteredList, sortCriteria)
        state.update { it.copy(coins = sortedList, isLoading = false) }
        //  state = state.copy(coins = sortedList, isLoading = false)
    }

    private suspend fun observeTickerCoins() {
        state.update { it.copy(isLoading = true) }
        // state = state.copy(isLoading = true)
        try {
            coinRepository.observeTickerCoins().collect { coins ->
                allCoinsData = coins
                sortAndFilterCoins()
            }
        } catch (e: Exception) {
            state.update { it.copy(isLoading = false, error = "An error occurred: ${e.localizedMessage}") }
            // state = state.copy(isLoading = false, error = "An error occurred: ${e.localizedMessage}")
        }
    }

    fun onSearchQueryUpdated(query: String) {
        searchQuery = query
        sortAndFilterCoins()
    }

    private suspend fun fetchTickerCoins() {
        coinRepository.fetchTickerCoins()
    }
}

package com.filip.cryptoViewer.presentation.ui.screens.cointickerlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.di.IoDispatcher
import com.filip.cryptoViewer.domain.model.SortCriteria
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.model.SortOrder
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.domain.usecase.SortAndFilterCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinTickerListViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val sortAndFilterCoinsUseCase: SortAndFilterCoinsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _sortCriteria = MutableStateFlow(SortCriteria(SortField.RANK, SortOrder.DESCENDING))

    // due to texfield nature and state handling
    // we need to separate the search query
    // flow from field that texfield is using for
    // updating content to avoid unintended behavior
    private val _searchQueryFlow = MutableStateFlow("")
    var searchQuery by mutableStateOf("")
        private set

    val state: StateFlow<CoinTickerListState> = combine(
        coinRepository.observeTickerCoins(),
        _searchQueryFlow,
        _sortCriteria,
    ) { allCoins, query, sortCriteria ->
        val filteredList = sortAndFilterCoinsUseCase.filterCoinList(query, allCoins)
        val sortedList = sortAndFilterCoinsUseCase.sortCoins(filteredList, sortCriteria)
        CoinTickerListState(coins = sortedList, isLoading = false, error = "")
    }
        .onStart {
            emit(CoinTickerListState.Empty.copy(isLoading = true))
            tryFetchingTickerCoins()
        }
        .flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CoinTickerListState.Empty,
        )

    private suspend fun FlowCollector<CoinTickerListState>.tryFetchingTickerCoins() {
        try {
            coinRepository.fetchTickerCoins()
        } catch (e: Exception) {
            val errorState = CoinTickerListState.Empty.copy(
                isLoading = false,
                error = e.message ?: "An unexpected error occurred",
            )
            emit(errorState)
        }
    }

    private var sortCriteria: SortCriteria
        get() = _sortCriteria.value
        set(value) {
            _sortCriteria.value = value
        }

    fun updateSortCriteria(field: SortField) {
        sortCriteria = if (sortCriteria.field == field) {
            sortCriteria.copy(order = if (sortCriteria.order == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING)
        } else {
            SortCriteria(field = field, order = SortOrder.ASCENDING)
        }
    }

    fun getArrowForField(field: SortField): String =
        if (sortCriteria.field == field) {
            if (sortCriteria.order == SortOrder.ASCENDING) "↑" else "↓"
        } else {
            ""
        }

    fun onSearchQueryUpdate(query: String) {
        searchQuery = query
        _searchQueryFlow.value = query
    }
}

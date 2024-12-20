package com.filip.cryptoViewer.presentation.ui.screens.coindetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.presentation.CoinDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val coinId = savedStateHandle.toRoute<CoinDetailScreen>().coinId

    val state: StateFlow<CoinDetailState> = flow {
        emit(CoinDetailState.Empty.copy(isLoading = true))
        emit(produceState())
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CoinDetailState.Empty,
        )

    private suspend fun produceState(): CoinDetailState {
        return try {
            val coinData = coinRepository.getCoinById(coinId)
            CoinDetailState(
                coin = coinData,
                isLoading = false,
                error = "",
            )
        } catch (e: Exception) {
            CoinDetailState(
                coin = null,
                isLoading = false,
                error = e.message ?: "An unexpected error occurred",
            )
        }
    }
}

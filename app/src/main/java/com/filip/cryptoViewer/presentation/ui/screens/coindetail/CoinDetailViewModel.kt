package com.filip.cryptoViewer.presentation.ui.screens.coindetail
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(CoinDetailState.Empty)
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoin(coinId)
            }
        }
    }

    private suspend fun getCoin(coinId: String) {
        state = state.copy(isLoading = true)

        try {
            val coinData = coinRepository.getCoinById(coinId)
            state = state.copy(
                coin = coinData,
                isLoading = false,
            )
        } catch (e: Exception) {
            state = state.copy(
                error = e.message ?: "An unexpected error occurred",
                isLoading = false,
            )
        }
    }
}

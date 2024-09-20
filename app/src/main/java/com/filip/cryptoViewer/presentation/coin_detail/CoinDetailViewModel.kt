package com.filip.cryptoViewer.presentation.coin_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state : State<CoinDetailState> = _state

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoin(coinId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getCoin(coinId: String) {
        _state.value = CoinDetailState(isLoading = true)  // Set loading state

        try {
            val coinData = coinRepository.getCoinById(coinId)

            _state.value = CoinDetailState(
                coin = coinData
            )
        } catch (e: Exception) {
            // Handle any errors that occur
            _state.value = CoinDetailState(
                error = e.message ?: "An unexpected error occurred"
            )
        }
    }
}

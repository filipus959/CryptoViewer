package com.filip.cryptoViewer.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filip.cryptoViewer.common.Constants
import com.filip.cryptoViewer.common.Resource
import com.filip.cryptoViewer.domain.use_case.get_specific_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state : State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId)
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Success -> CoinDetailState(coin = result.data)
                    is Resource.Error -> CoinDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                    is Resource.Loading -> CoinDetailState(isLoading = true)
                }
            }
            .launchIn(viewModelScope) // This line handles the coroutine context implicitly
    }
}

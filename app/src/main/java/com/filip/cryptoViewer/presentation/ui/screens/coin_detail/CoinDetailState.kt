package com.filip.cryptoViewer.presentation.ui.screens.coin_detail

import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.presentation.UIState
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterState

data class CoinDetailState(
    override val isLoading: Boolean,
    val coin: CoinDetail?,
    override val error: String
) : UIState
{
    companion object {
        val Empty = CoinDetailState(
            isLoading = false,
            error = "",
            coin = null,
        )
    }
}
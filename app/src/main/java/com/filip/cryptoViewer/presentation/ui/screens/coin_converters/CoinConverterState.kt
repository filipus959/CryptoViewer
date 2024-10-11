package com.filip.cryptoViewer.presentation.ui.screens.coin_converters

import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.UIState

data class CoinConverterState(
    override val isLoading: Boolean,
    override val error: String,
    val coins : List<CoinTickerItem>?
) : UIState
{
    companion object {
        val Empty = CoinConverterState(
            isLoading = false,
            error = "",
            coins = emptyList()
        )
    }
}


package com.filip.cryptoViewer.presentation.ui.screens.coin_converters

import com.filip.cryptoViewer.domain.model.CoinTickerItem

data class CoinConverterState(
    val isLoading: Boolean,
    val error: String,
    val coins : List<CoinTickerItem>?
)
{
    companion object {
        val Empty = CoinConverterState(
            isLoading = false,
            error = "",
            coins = emptyList()
        )
    }
}


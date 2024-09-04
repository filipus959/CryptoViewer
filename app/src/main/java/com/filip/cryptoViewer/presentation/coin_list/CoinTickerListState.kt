package com.filip.cryptoViewer.presentation.coin_list

import com.filip.cryptoViewer.domain.model.CoinTickerItem


data class CoinTickerListState(
    val isLoading: Boolean,
    val coins: List<CoinTickerItem>,
    val error: String,
) {
    companion object {
        val Empty = CoinTickerListState(
            isLoading = false,
            coins = emptyList(),
            error = ""
        )
    }
}

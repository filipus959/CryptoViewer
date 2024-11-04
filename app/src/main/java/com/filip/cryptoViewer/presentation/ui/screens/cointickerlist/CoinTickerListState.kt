package com.filip.cryptoViewer.presentation.ui.screens.cointickerlist

import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.UIState

data class CoinTickerListState(
    override val isLoading: Boolean,
    val coins: List<CoinTickerItem>,
    override val error: String,
) : UIState {
    companion object {
        val Empty = CoinTickerListState(
            isLoading = false,
            coins = emptyList(),
            error = "",
        )
    }
}

package com.filip.cryptoViewer.presentation.coin_list

import com.filip.cryptoViewer.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = "",
    val searchQuery: String = ""

)
{
    companion object {
        val Empty = CoinListState()
    }
}
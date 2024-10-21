package com.filip.cryptoViewer.presentation.ui.screens.coin_chart

import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.presentation.UIState
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterState

data class CoinChartState(
    override val isLoading: Boolean,
    val coins: List<CoinChart>?,
    override val error: String,
    val id: String,
    val marketCap: String,
    val name: String
) : UIState
{
    companion object {
        val Empty = CoinChartState(
            isLoading = false,
            error = "",
            coins = emptyList(),
            id = "",
            marketCap = "",
            name = "",
        )
    }
}
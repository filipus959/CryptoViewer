package com.filip.cryptoViewer.presentation.ui.screens.coin_chart

import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.presentation.UIState

data class CoinChartState(
    override val isLoading: Boolean = false,
    val coins: List<CoinChart>? = emptyList(),
    override val error: String = "",
    val id: String = "",
    val marketCap: String = "",
    val name: String = ""
) : UIState
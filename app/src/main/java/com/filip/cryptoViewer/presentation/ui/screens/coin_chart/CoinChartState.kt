package com.filip.cryptoViewer.presentation.ui.screens.coin_chart

import com.filip.cryptoViewer.domain.model.CoinChart

data class CoinChartState(
    val isLoading: Boolean = false,
    val coins: List<CoinChart>? = emptyList(),
    val error: String = "",
    val id: String = "",
    val marketCap: String = "",
    val name: String = ""
)
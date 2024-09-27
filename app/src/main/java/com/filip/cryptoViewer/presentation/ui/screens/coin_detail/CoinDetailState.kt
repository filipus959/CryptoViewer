package com.filip.cryptoViewer.presentation.ui.screens.coin_detail

import com.filip.cryptoViewer.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
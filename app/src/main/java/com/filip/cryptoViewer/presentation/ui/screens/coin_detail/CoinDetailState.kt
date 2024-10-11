package com.filip.cryptoViewer.presentation.ui.screens.coin_detail

import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.presentation.UIState

data class CoinDetailState(
    override val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    override val error: String = ""
) : UIState
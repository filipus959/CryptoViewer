package com.filip.cryptoViewer.presentation.ui.screens.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterScreenContent
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterState
import com.filip.cryptoViewer.presentation.ui.theme.CryptoViewerTheme
@Preview(name = "Light Mode")
@Composable
fun PreviewLightCoinConverterScreen() {

        CryptoViewerTheme(darkTheme = false) { // Explicitly set light mode
            PreviewCoinConverterScreen()

    }

}

@Preview(name = "Dark Mode")
@Composable
fun PreviewDarkCoinConverterScreen() {

        CryptoViewerTheme(darkTheme = true) { // Explicitly set dark mode
            PreviewCoinConverterScreen()

    }

}
@Composable
fun PreviewCoinConverterScreen() {
    val mockState = CoinConverterState(
        coins = listOf(
            CoinTickerItem(id = "1", name = "Bitcoin", symbol = "BTC", rank = 0, usdPrice = 0.0, percentChange24h = 0.0),
            CoinTickerItem(id = "2", name = "Ethereum", symbol = "ETH", rank = 0, usdPrice = 0.0, percentChange24h = 0.0),
            CoinTickerItem(id = "3", name = "Ripple", symbol = "XRP", rank = 0, usdPrice = 0.0, percentChange24h = 0.0)
        ), error = "", isLoading = false
    )

    val selectedCoin1 = CoinTickerItem(id = "1", name = "Bitcoin", symbol = "BTC", rank = 0, usdPrice = 0.0, percentChange24h = 0.0)
    val selectedCoin2 = CoinTickerItem(id = "2", name = "Ethereum", symbol = "ETH", rank = 0, usdPrice = 0.0, percentChange24h = 0.0)
    val result = "1 BTC = 30 ETH"
    val searchQuery = ""
    val amount = 1
    Surface {
        CryptoViewerTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                CoinConverterScreenContent(
                    state = mockState,
                    selectedCoin1 = selectedCoin1,
                    selectedCoin2 = selectedCoin2,
                    result = result,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { /* Mock action */ },
                    onFirstSelection = { /* Mock action */ },
                    onSecondSelection = { /* Mock action */ },
                    onAmountChange = { /* Mock action */ },
                    amount = amount,
                )
            }
        }
    }


}
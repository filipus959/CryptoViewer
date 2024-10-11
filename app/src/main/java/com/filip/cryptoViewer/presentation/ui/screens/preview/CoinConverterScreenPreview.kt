package com.filip.cryptoViewer.presentation.ui.screens.preview

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterState
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterViewModel
import com.filip.cryptoViewer.presentation.ui.theme.CryptoViewerTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CoinConverterScreenPreviewLight() {
    CryptoViewerTheme {


        // Use the fake repository to simulate coin data
        val fakeRepository = FakeCoinRepository()

        // Create a ViewModel using the fake repository
        val viewModel = CoinConverterViewModel(fakeRepository).apply {
            // Set the mock state for preview
            state = CoinConverterState(
                coins = listOf(
                    CoinTickerItem(
                        id = "bitcoin",
                        name = "Bitcoin",
                        rank = 1,
                        symbol = "BTC",
                        percentChange24h = 1.5,
                        usdPrice = 30000.0
                    ),
                    CoinTickerItem(
                        id = "ethereum",
                        name = "Ethereum",
                        rank = 2,
                        symbol = "ETH",
                        percentChange24h = -0.8,
                        usdPrice = 2000.0
                    ),
                    CoinTickerItem(
                        id = "litecoin",
                        name = "Litecoin",
                        rank = 3,
                        symbol = "LTC",
                        percentChange24h = 0.3,
                        usdPrice = 150.0
                    )
                ), isLoading = false,
                error = ""

            )
            selectedCoin1 = state.coins!![0]  // Bitcoin
            selectedCoin2 = state.coins!![1]  // Ethereum
            result = "0.0667 BTC"
        }

        CoinConverterScreen(
            viewModel = viewModel,
        )
    }
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CoinConverterScreenPreviewDark() {
    CryptoViewerTheme(darkTheme = true) {


        // Use the fake repository to simulate coin data
        val fakeRepository = FakeCoinRepository()

        // Create a ViewModel using the fake repository
        val viewModel = CoinConverterViewModel(fakeRepository).apply {
            // Set the mock state for preview
            state = CoinConverterState(
                coins = listOf(
                    CoinTickerItem(
                        id = "bitcoin",
                        name = "Bitcoin",
                        rank = 1,
                        symbol = "BTC",
                        percentChange24h = 1.5,
                        usdPrice = 30000.0
                    ),
                    CoinTickerItem(
                        id = "ethereum",
                        name = "Ethereum",
                        rank = 2,
                        symbol = "ETH",
                        percentChange24h = -0.8,
                        usdPrice = 2000.0
                    ),
                    CoinTickerItem(
                        id = "litecoin",
                        name = "Litecoin",
                        rank = 3,
                        symbol = "LTC",
                        percentChange24h = 0.3,
                        usdPrice = 150.0
                    )
                ), isLoading = false,
                error = ""

            )
            selectedCoin1 = state.coins!![0]  // Bitcoin
            selectedCoin2 = state.coins!![1]  // Ethereum
            result = "0.0667 BTC"
        }

        CoinConverterScreen(
            viewModel = viewModel,
        )
    }
}
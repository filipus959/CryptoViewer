package com.filip.cryptoViewer.presentation.ui.screens.coin_converters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.ui.LoadableScreen

@Composable
fun CoinConverterScreen(
    viewModel: CoinConverterViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    LoadableScreen(state) {
        CoinConverterScreenContent(
            state = state,
            selectedCoin1 = viewModel.selectedCoin1,
            selectedCoin2 = viewModel.selectedCoin2,
            result = viewModel.result,
            amount = viewModel.amount,
            searchQuery = viewModel.searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryUpdated,
            onFirstSelection = viewModel::firstSelection,
            onSecondSelection = viewModel::secondSelection,
            onAmountChange = viewModel::onAmountChange,
        )
    }

}

@Composable
fun BoxScope.CoinConverterScreenContent(
    state: CoinConverterState,
    selectedCoin1: CoinTickerItem?,
    selectedCoin2: CoinTickerItem?,
    result: String,
    amount: Int,
    searchQuery: String,
    onAmountChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onFirstSelection: (CoinTickerItem) -> Unit,
    onSecondSelection: (CoinTickerItem) -> Unit,
) {
    state.coins?.let { coins ->
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Converter", style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Selected coins and conversion result
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CoinBox(text = selectedCoin1?.name ?: "Select Coin 1", padding = 8.dp)
                Text(
                    "->",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                CoinBox(text = selectedCoin2?.name ?: "Select Coin 2", padding = 8.dp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                TextField(
                    value = amount.toString(), // Display the amount as string
                    onValueChange = { newValue ->
                        // Allow only digits and decimal point
                        if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' }) {
                            onAmountChange(newValue)
                        }
                    },
                    modifier = Modifier.wrapContentSize()
                        .widthIn(min = 20.dp, max = 200.dp),
                    label = { Text("Amount") }, // Placeholder text
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal // Numeric keyboard
                    )
                )


                Text(
                    text = result,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }


            Spacer(modifier = Modifier.height(26.dp))

            // Search field
            SearchField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = "Search Coins",
                modifier = Modifier.fillMaxWidth(0.9f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp)) // Add extra space between search field and lists

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                //  .padding(bottom = navBarPadding.calculateBottomPadding() / 2 + 20.dp)
                .fillMaxHeight(0.5f)
        ) {
            LazyColumnWithLabel(
                label = "From:",
                coins = coins,
                onSelectCoin = onFirstSelection,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            LazyColumnWithLabel(
                label = "To:",
                coins = coins,
                onSelectCoin = onSecondSelection,
                modifier = Modifier.weight(1f)
            )
        }
    }

}

@Composable
fun LazyColumnWithLabel(
    label: String,
    coins: List<CoinTickerItem>,
    onSelectCoin: (CoinTickerItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(coins) { coin ->
                ListItem(coin = coin, onItemClick = { onSelectCoin(coin) })
            }
        }
    }
}

@Composable
fun ListItem(
    coin: CoinTickerItem, onItemClick: (String) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(coin.id) }
        .padding(20.dp)) {
        Text(
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(30.dp))
    }
}

@Composable
fun CoinBox(text: String, padding: Dp) {
    Box(
        modifier = Modifier.padding(padding)
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun SearchField(
    value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true
    )
}

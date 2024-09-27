import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.presentation.ui.screens.coin_converters.CoinConverterViewModel

@Composable
fun CoinConverterScreen(
    viewModel: CoinConverterViewModel = hiltViewModel(),
    navBarPadding: PaddingValues
) {
    val state = viewModel.state
    Box(modifier = Modifier.fillMaxSize()) {
        state.coins?.let { coins ->
            // Title
            Text(
                text = "Converter",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 36.dp)
            )

            // Selected Coins, Colon, Result, and Convert Button
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CoinBox(text = viewModel.selectedCoin1.name, padding = 8.dp)
                    Text(
                        "->",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    CoinBox(text = viewModel.selectedCoin2.name, padding = 8.dp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.result,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.runExchangeRate() }) {
                    Text("Convert")
                }
                Spacer(modifier = Modifier.height(56.dp))
                SearchField(
                    value = viewModel.searchQuery,
                    onValueChange = viewModel::onSearchQueryUpdated,
                    label = "Search Coins",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                )
            }

            // LazyColumns for From and To coin lists
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = navBarPadding.calculateBottomPadding() / 2 + 20.dp)
                    .fillMaxHeight(0.5f)  // Limit the height to 50% of the screen
            ) {
                LazyColumnWithLabel(
                    label = "From:",
                    coins = coins,
                    onSelectCoin = { viewModel.firstSelection(it) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                LazyColumnWithLabel(
                    label = "To:",
                    coins = coins,
                    onSelectCoin = { viewModel.secondSelection(it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun CoinBox(text: String, padding: Dp) {
    Box(
        modifier = Modifier
            //     .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(padding)
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun LazyColumnWithLabel(
    label: String,
    coins: List<CoinTickerItem>,  // Assume you're using CoinTickerItem here
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
            modifier = Modifier.fillMaxHeight() // Make sure each column takes the remaining height in the Row
        ) {
            items(coins) { coin ->
                ListItem(coin = coin, onItemClick = { onSelectCoin(coin) })
            }
        }
    }
}

// Modified ListItem based on your provided structure
@Composable
fun ListItem(
    coin: CoinTickerItem,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin.id) }
            .padding(20.dp),
    ) {
        // Display coin name and symbol
        Text(
            text = "${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(30.dp))

        // Display coin price if needed (commented out)
        // Uncomment if you want to display price
//        Text(
//            text = "$" + formatter(coin.usdPrice),
//            modifier = Modifier
//                .padding(start = 12.dp)
//                .weight(0.8f),
//            textAlign = TextAlign.Start
//        )
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
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

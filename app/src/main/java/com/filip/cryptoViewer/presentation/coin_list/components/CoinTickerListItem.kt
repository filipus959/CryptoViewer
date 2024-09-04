package com.filip.cryptoViewer.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.filip.cryptoViewer.domain.model.CoinTickerItem


@Composable
fun CoinTickerListItem(
    coin: CoinTickerItem,
    onItemClick: (CoinTickerItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${coin.rank}. ${coin.name} ${coin.symbol} ${coin.quotes.USD.price}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )

    }
}
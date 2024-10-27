package com.filip.cryptoViewer.presentation.ui.screens.cointickerlist.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.filip.cryptoViewer.domain.model.CoinTickerItem

@Composable
fun CoinTickerListItem(
    coin: CoinTickerItem,
    onItemClick: (CoinTickerItem) -> Unit,
) {
    val pChange = coin.percentChange24h

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(20.dp),
        verticalAlignment = CenterVertically,
    ) {
        Text(
            text = "${coin.rank}. ${coin.name} ${coin.symbol}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),

        )
        Spacer(modifier = Modifier.width(30.dp))

        Text(
            text = "$" + formatter(coin.usdPrice),
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(0.8f),
            textAlign = TextAlign.Start,
        )
        Text(
            text = if (pChange > 0) "+$pChange%" else "$pChange%",
            color = if (pChange > 0) Color.Green else Color.Red,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),

        )
    }
}

@SuppressLint("DefaultLocale")
val formatter: (Double) -> String = { value ->
    when {
        value < 10 -> {
            String.format("%.6f", value)
        }

        else -> {
            String.format("%.2f", value)
        }
    }
}

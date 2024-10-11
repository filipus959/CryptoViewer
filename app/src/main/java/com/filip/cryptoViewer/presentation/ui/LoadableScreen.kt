package com.filip.cryptoViewer.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.filip.cryptoViewer.presentation.UIState
import com.filip.cryptoViewer.presentation.ui.theme.CryptoViewerTheme

@Composable
fun <T : UIState> LoadableScreen(
    state: T,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopCenter,
    content: @Composable BoxScope.(T) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        content(state)

        // Error Message
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        // Loading Indicator
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
private fun LoadableScreenPreview() {
    CryptoViewerTheme {
        LoadableScreen(
            modifier = Modifier.background(Color.White),
            state = object : UIState {
                override var isLoading: Boolean = true
                override var error: String = "12312"
            }
        ) {
            Box(
                Modifier
                    .size(50.dp)
                    .background(Color.Blue)
            )
        }
    }
}
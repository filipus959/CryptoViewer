package com.filip.cryptoViewer.presentation.ui.screens.coinchart.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.filip.cryptoViewer.R

@Composable
fun ChartRangeMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSelectRange: (Int) -> Unit,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.range_365_days)) },
            onClick = { onSelectRange(365); onDismissRequest() },
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.range_30_days)) },
            onClick = { onSelectRange(30); onDismissRequest() },
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.range_7_days)) },
            onClick = { onSelectRange(7); onDismissRequest() },
        )
    }
}

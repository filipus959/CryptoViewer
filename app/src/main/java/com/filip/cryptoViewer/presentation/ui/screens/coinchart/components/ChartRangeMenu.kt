package com.filip.cryptoViewer.presentation.ui.screens.coinchart.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ChartRangeMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSelectRange: (Int) -> Unit,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Last 365 Days") },
            onClick = { onSelectRange(365); onDismissRequest() },
        )
        DropdownMenuItem(
            text = { Text("Last 30 Days") },
            onClick = { onSelectRange(30); onDismissRequest() },
        )
        DropdownMenuItem(
            text = { Text("Last 7 Days") },
            onClick = { onSelectRange(7); onDismissRequest() },
        )
    }
}

package com.filip.cryptoViewer.presentation.coin_chart.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChartRangeMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSelectRange: (Int) -> Unit,
) {
  //  var text by remember { mutableStateOf("") }
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        DropdownMenuItem(
            text = { Text("Last 365 Days") },
            onClick = { onSelectRange(365);onDismissRequest() })
        DropdownMenuItem(
            text = { Text("Last 30 Days") },
            onClick = { onSelectRange(30);onDismissRequest() })
        DropdownMenuItem(
            text = { Text("Last 7 Days") },
            onClick = { onSelectRange(7);onDismissRequest() })
//        TextField(
//            value = text,
//            onValueChange = { newText -> text = newText;onSelectRange(text.toInt()) },
//            label = { Text("Custom") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number
//            )
//        )

    }
}
package com.filip.cryptoViewer.presentation.ui.screens.coin_chart


import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.presentation.ui.LoadableScreen
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components.ChartRangeMenu
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components.ChartStyle
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components.DataPoint
import com.plcoding.cryptotracker.crypto.presentation.coin_detail.PriceChart

@Composable
fun CoinChartScreen(
    viewModel: CoinChartViewModel = hiltViewModel(), goToDetails: (String) -> Unit
) {
    val state = viewModel.state
    var expanded by remember { mutableStateOf(false) }

    LoadableScreen(state = state) {
        CoinChartScreenContent(state = state,
            expanded = expanded,
            onExpandClick = { expanded = true },
            onDismissExpand = { expanded = false },
            onSelectRange = { days ->
                viewModel.changeChartRange(days)
                expanded = false
            },
            goToDetails = goToDetails,
            dataPoints = viewModel.getDataPoints()
        )
    }
}

@Composable
fun CoinChartScreenContent(
    state: CoinChartState,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    onDismissExpand: () -> Unit,
    onSelectRange: (Int) -> Unit,
    goToDetails: (String) -> Unit,
    dataPoints: List<DataPoint>
) {
    state.coins?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price Chart for ${state.id}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onExpandClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Range")
                    ChartRangeMenu(
                        expanded = expanded,
                        onDismissRequest = onDismissExpand,
                        onSelectRange = onSelectRange
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                var selectedDataPoint by remember {
                    mutableStateOf<DataPoint?>(null)
                }
                var labelWidth by remember {
                    mutableFloatStateOf(0f)
                }
                var totalChartWidth by remember {
                    mutableFloatStateOf(0f)
                }
                val amountOfVisibleDataPoints = if (labelWidth > 0) {
                    ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                } else {
                    0
                }
                val startIndex = (dataPoints.lastIndex - amountOfVisibleDataPoints).coerceAtLeast(0)
                PriceChart(dataPoints = dataPoints,
                    style = ChartStyle(
                        chartLineColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.3f
                        ),
                        selectedColor = MaterialTheme.colorScheme.primary,
                        helperLinesThicknessPx = 5f,
                        axisLinesThicknessPx = 5f,
                        labelFontSize = 14.sp,
                        minYLabelSpacing = 25.dp,
                        verticalPadding = 8.dp,
                        horizontalPadding = 8.dp,
                        xAxisLabelSpacing = 8.dp
                    ),
                    visibleDataPointsIndices = startIndex..dataPoints.lastIndex,
                    unit = "$",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { totalChartWidth = it.width.toFloat() },
                    selectedDataPoint = selectedDataPoint,
                    onSelectedDataPoint = {
                        selectedDataPoint = it
                    },
                    onXLabelWidthChange = { labelWidth = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Market cap:",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = formatNumberWithCommas(state.marketCap) + "$",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { goToDetails(state.id) }) {
                Text(text = "More info about the coin")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

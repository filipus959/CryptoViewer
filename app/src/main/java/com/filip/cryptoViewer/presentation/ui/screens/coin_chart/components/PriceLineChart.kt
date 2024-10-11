package com.filip.cryptoViewer.presentation.ui.screens.coin_chart.components

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.filip.cryptoViewer.domain.model.CoinChart
import com.filip.cryptoViewer.presentation.ui.screens.coin_chart.CoinChartViewModel
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.LineChartData.Point
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.NoPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation


@SuppressLint("DefaultLocale")
@Composable
fun PriceLineChart(
    prices: List<CoinChart>,
) {
    val viewModel: CoinChartViewModel = hiltViewModel()
    val darkTheme: Boolean = isSystemInDarkTheme()
    val points = prices.map { coin ->
        Point(coin.price.toFloat(), "")
    }

    val lineChartData = LineChartData(
        points =  points,
        lineDrawer = SolidLineDrawer(color = MaterialTheme.colorScheme.primary),
        startAtZero = false,
    )

    // Set the chart to fill the available width
    LineChart(
        modifier = Modifier.fillMaxWidth(),
        pointDrawer = NoPointDrawer,
        animation = simpleChartAnimation(),
        linesChartData = listOf(lineChartData),
        yAxisDrawer = SimpleYAxisDrawer(labelTextColor = if(darkTheme) Color.White else Color.Black, labelValueFormatter = { value -> labelValueFormatter(value) +"$" } ),
        xAxisDrawer = SimpleXAxisDrawer(labelTextColor = if(darkTheme) Color.White else Color.Black),
        labels = viewModel.getTimeStamps()
    )
}




@SuppressLint("DefaultLocale")
val labelValueFormatter: (Float) -> String = { value ->
    when {
        value >= 10000 -> {
            // Round to the nearest thousand for values >= 10000
          //  val roundedValue = (value / 1000).roundToInt() * 1000
            String.format("%.0f", value)
        }
        value >= 1000 -> {
            // If value is between 1000 and 9999.99, show without decimal digits
            String.format("%.0f", value)
        }
        value < 1 -> {
            String.format("%.6f", value)
        }
        else -> {
            // If value is less than 1000, show with 2 decimal digits
            String.format("%.4f", value)
        }
    }
}


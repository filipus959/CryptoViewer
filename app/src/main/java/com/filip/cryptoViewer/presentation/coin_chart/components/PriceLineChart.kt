package com.filip.cryptoViewer.presentation.coin_chart.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.filip.cryptoViewer.domain.model.CoinChart
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.LineChartData.Point
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.NoPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PriceLineChart(prices: List<CoinChart>) {

    // Prepare the points for the chart
    val points = prices.map { coin ->
        Point(coin.price.toFloat(), "")
    }

    val lineChartData = LineChartData(
        points = points,
        lineDrawer = SolidLineDrawer(color = MaterialTheme.colorScheme.primary),
        startAtZero = false,
    )

    // Set the chart to fill the available width
    LineChart(
        modifier = Modifier.fillMaxWidth(),
        pointDrawer = NoPointDrawer,
        animation = simpleChartAnimation(),
        linesChartData = listOf(lineChartData),
        labels = getNext12Months()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNext12Months(): List<String> {
    val currentDate = LocalDate.now()
    val months = mutableListOf<String>()

    var date = currentDate.minusMonths(11)  // Start from 11 months ago to include the current month in the list

    repeat(12) {
        months.add(date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()))
        date = date.plusMonths(1)
    }

    return months
}
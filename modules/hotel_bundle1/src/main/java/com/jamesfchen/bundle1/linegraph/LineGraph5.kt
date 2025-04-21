package com.jamesfchen.bundle1.linegraph

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Apr/28/2024  Sun
 */
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jamesfchen.bundle1.ui.theme.PlotTheme
import com.jamesfchen.bundle1.ui.theme.Red100
import com.jamesfchen.bundle1.ui.theme.Red300
import com.jamesfchen.bundle1.ui.theme.Red500
import com.jamesfchen.bundle1.ui.theme.Yellow700
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
internal fun LineGraph5(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(color = Red300),
                    LinePlot.Intersection(color = Red500),
                    LinePlot.Highlight(color = Yellow700),
                )
            ),
            horizontalExtraSpace = 12.dp,
            xAxis = LinePlot.XAxis(unit = 0.1f, roundToInt = false),
            yAxis = LinePlot.YAxis(steps = 4, roundToInt = false),
            grid = LinePlot.Grid(Red100, steps = 4),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LineGraph5Preview() {
    PlotTheme {
        LineGraph5(listOf(DataPoints.dataPoints1, DataPoints.dataPoints2))
    }
}
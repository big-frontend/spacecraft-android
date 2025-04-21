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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jamesfchen.bundle1.ui.theme.Green900
import com.jamesfchen.bundle1.ui.theme.LightGreen600
import com.jamesfchen.bundle1.ui.theme.PlotTheme
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
internal fun LineGraph1(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(LightGreen600, 2.dp),
                    LinePlot.Intersection(LightGreen600, 5.dp),
                    LinePlot.Highlight(Green900, 5.dp),
                    LinePlot.AreaUnderLine(LightGreen600, 0.3f)
                ),
                LinePlot.Line(
                    lines[1],
                    LinePlot.Connection(Color.Gray, 2.dp),
                    LinePlot.Intersection { center, _ ->
                        val px = 2.dp.toPx()
                        val topLeft = Offset(center.x - px, center.y - px)
                        drawRect(Color.Gray, topLeft, Size(px * 2, px * 2))
                    },
                ),
            ),
            selection = LinePlot.Selection(
                highlight = LinePlot.Connection(
                    Green900,
                    strokeWidth = 2.dp,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
                )
            ),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LineGraph1Preview() {
    PlotTheme {
        LineGraph1(listOf(DataPoints.dataPoints1, DataPoints.dataPoints2))
    }
}
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jamesfchen.bundle1.ui.theme.PlotTheme
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
internal fun LineGraph2(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[1],
                    LinePlot.Connection(Color.Gray, 2.dp),
                    null,
                    LinePlot.Highlight { center ->
                        val color = Color.Gray
                        drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                        drawCircle(color, 6.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                ),
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(Color.Blue, 3.dp),
                    LinePlot.Intersection(Color.Blue, 6.dp) { center, point ->
                        val x = point.x
                        val rad = if (x % 4f == 0f) 6.dp else 3.dp
                        drawCircle(
                            Color.Blue,
                            rad.toPx(),
                            center,
                        )
                    },
                    LinePlot.Highlight { center ->
                        val color = Color.Blue
                        drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                        drawCircle(color, 6.dp.toPx(), center)
                        drawCircle(Color.White, 3.dp.toPx(), center)
                    },
                    LinePlot.AreaUnderLine(Color.Blue, 0.1f)
                ),
            ), LinePlot.Grid(Color.Gray), paddingRight = 16.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun LineGraph2Preview() {
    PlotTheme {
        LineGraph2(listOf(DataPoints.dataPoints1, DataPoints.dataPoints2))
    }
}
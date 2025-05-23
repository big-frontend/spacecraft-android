package com.electrolytej.bundle1.linegraph

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Apr/28/2024  Sun
 */
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.electrolytej.bundle1.RoundRectangle
import com.electrolytej.bundle1.toPx
import com.electrolytej.bundle1.theme.GreyCustom
import com.electrolytej.bundle1.theme.PlotTheme
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import java.text.DecimalFormat

@Composable
internal fun LineGraph4(lines: List<List<DataPoint>>, modifier: Modifier) {
    val totalWidth = remember { mutableStateOf(0) }
    Column(Modifier.onGloballyPositioned {
        totalWidth.value = it.size.width
    }) {
        val xOffset = remember { mutableStateOf(0f) }
        val cardWidth = remember { mutableStateOf(0) }
        val visibility = remember { mutableStateOf(false) }
        val points = remember { mutableStateOf(listOf<DataPoint>()) }
        val density = LocalDensity.current

        Box(Modifier.height(150.dp)) {
            if (visibility.value) {
                Surface(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned {
                            cardWidth.value = it.size.width
                        }
                        .graphicsLayer(translationX = xOffset.value),
                    shape = RoundRectangle,
                    color = GreyCustom
                ) {
                    Column(
                        Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        val value = points.value
                        if (value.isNotEmpty()) {
                            val x = DecimalFormat("#.#").format(value[0].x)
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = "Score at $x:00 hrs",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.Gray
                            )
                            ScoreRow("Today", value[1].y, Color.Blue)
                            ScoreRow("Yesterday", value[0].y, Color.Gray)
                        }
                    }
                }

            }
        }
        val padding = 16.dp
        MaterialTheme(colors = MaterialTheme.colors.copy(surface = Color.White)) {
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
                            LinePlot.Connection(),
                            LinePlot.Intersection(),
                            LinePlot.Highlight { center ->
                                val color = Color.Blue
                                drawCircle(color, 9.dp.toPx(), center, alpha = 0.3f)
                                drawCircle(color, 6.dp.toPx(), center)
                                drawCircle(Color.White, 3.dp.toPx(), center)
                            },
                            LinePlot.AreaUnderLine()
                        ),
                    ),
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = padding),
                onSelectionStart = { visibility.value = true },
                onSelectionEnd = { visibility.value = false }
            ) { x, pts ->
                val cWidth = cardWidth.value.toFloat()
                var xCenter = x + padding.toPx(density)
                xCenter = when {
                    xCenter + cWidth / 2f > totalWidth.value -> totalWidth.value - cWidth
                    xCenter - cWidth / 2f < 0f -> 0f
                    else -> xCenter - cWidth / 2f
                }
                xOffset.value = xCenter
                points.value = pts
            }
        }
    }
}

@Composable
private fun ScoreRow(title: String, value: Float, color: Color) {
    val formatted = DecimalFormat("#.#").format(value)
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            Image(
                painter = ColorPainter(color), contentDescription = "Line color",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 4.dp)
                    .size(10.dp)
                    .clip(RoundRectangle)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = Color.DarkGray
            )
        }
        Text(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterEnd),
            text = formatted,
            style = MaterialTheme.typography.subtitle2,
            color = Color.DarkGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LineGraph4Preview() {
    PlotTheme {
        LineGraph4(listOf(DataPoints.dataPoints1, DataPoints.dataPoints2), Modifier)
    }
}
package com.electrolytej.bundle1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.electrolytej.bundle1.theme.Red100
import com.electrolytej.bundle1.theme.Red300
import com.electrolytej.bundle1.theme.Red500
import com.electrolytej.bundle1.theme.SpacecraftAndroidTheme
import com.electrolytej.bundle1.theme.Yellow700
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot


@Composable
fun HotelScreen() {
    SpacecraftAndroidTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Button(
        onClick = {
            Log.d("cjf","click")
        }
    ) {
        Text(text = "Hello $name!")

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpacecraftAndroidTheme {
        Greeting("Android")
    }
}

@Composable
fun SampleLineGraph(lines: List<List<DataPoint>>) {
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
            grid = LinePlot.Grid(Red100, steps = 4),
        ),
        modifier = Modifier.fillMaxWidth().height(200.dp),
        onSelection = { xLine, points ->
            // Do whatever you want here
        }
    )
}
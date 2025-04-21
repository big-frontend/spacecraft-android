package com.jamesfchen.bundle1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jamesfchen.bundle1.ui.theme.Red100
import com.jamesfchen.bundle1.ui.theme.Red300
import com.jamesfchen.bundle1.ui.theme.Red500
import com.jamesfchen.bundle1.ui.theme.SpacecraftAndroidTheme
import com.jamesfchen.bundle1.ui.theme.Yellow700
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import androidx.compose.ui.Modifier
import androidx.tv.material3.Button

class Bundle1Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpacecraftAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
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
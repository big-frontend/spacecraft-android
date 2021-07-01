package com.jamesfchen.bundle2

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jamesfchen.bundle2.ui.theme.SpacecraftAndroidTheme

class Bundle2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpacecraftAndroidTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("hotel bundle2")
//                }
                ArtistCard()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpacecraftAndroidTheme {
        Greeting("hotel bundle2")
    }
}

@Composable
fun ArtistCard() {
    Column {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}
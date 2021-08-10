package com.jamesfchen.bundle2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jamesfchen.bundle2.customview.scrollExprimental.NestedScrollActivity
import com.jamesfchen.bundle2.kk.PagerViewActivity
import com.jamesfchen.bundle2.ui.theme.SpacecraftAndroidTheme
import java.io.File

class Bundle2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File("/data/local/tmp/uidump.xml")
//        if (file.exists()) {
//            file.inputStream().use { fis ->
//                val content = fis.reader().readText()
//                Log.d("cjf", "content:${content}")
//            }
//        }
        setContent {
            SpacecraftAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("hotel bundle2")
                }
                ArtistCard {
//                    startActivity(FlutterActivity.createDefaultIntent(Bundle2Activity.this))
//                    startActivity(Intent(Bundle2Activity@ this, DispatchEventActivity::class.java))
                    startActivity(Intent(Bundle2Activity@this, PagerViewActivity::class.java))
                }
            }
        }
        startActivity(Intent(Bundle2Activity@this, PagerViewActivity::class.java))
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
fun ArtistCard(onClick: () -> Unit) {
    Column {
        Text("Alfred Sisley")
        Text("3 minutes ago")
        Button(onClick = onClick) {

        }
    }
}
package com.electrolytej.bundle1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.electrolytej.bundle1.widget.Shoe3DPreview

class Shoe3DPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val modelUrl = intent.getStringExtra(EXTRA_MODEL_URL)
            ?: "https://modelviewer.dev/shared-assets/models/Astronaut.glb"
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Shoe3DPreview(
                        modelUrl = modelUrl,
                        modifier = Modifier.Companion.fillMaxSize(),
                        autoRotate = true,
                        cameraControls = true,
                        backgroundColor = "#FFFFFF",
                        onReady = {
                            Log.d("Shoe3DPreview", "Model loaded!")
                        },
                        onError = { error ->
                            Log.e("Shoe3DPreview", "Error: $error")
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_MODEL_URL = "extra_model_url"
    }
}
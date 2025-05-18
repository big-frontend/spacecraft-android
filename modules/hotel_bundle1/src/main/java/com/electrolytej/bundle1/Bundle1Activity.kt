package com.electrolytej.bundle1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class Bundle1Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           HotelScreen()
        }
    }
}
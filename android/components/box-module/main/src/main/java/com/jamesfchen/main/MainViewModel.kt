package com.jamesfchen.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class MainViewModel :ViewModel(){
    val countState = mutableStateOf(0)
    init {
    }
}
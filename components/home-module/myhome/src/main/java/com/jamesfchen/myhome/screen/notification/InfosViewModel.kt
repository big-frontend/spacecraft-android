package com.jamesfchen.myhome.screen.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class InfosViewModel(val app: Application) : AndroidViewModel(app) {
    val infoFlow = flow<Int> {
        repeat(300) {
            emit(it)
            delay(1000)
        }
    }


}
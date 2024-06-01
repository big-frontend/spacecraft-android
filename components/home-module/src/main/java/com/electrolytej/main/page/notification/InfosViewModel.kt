package com.electrolytej.main.page.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
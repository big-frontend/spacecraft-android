package com.jamesfchen.loader.androidxstartup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace
import com.jamesfchen.loader.ImageLoader

class ImageInitializer : Initializer<Unit> {
    @AddTrace(name = "ImageInitializer#create",enabled = true)
    override fun create(context: Context) {
        Log.d("cjf","ImageInitializer#create")
        ImageLoader.init()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
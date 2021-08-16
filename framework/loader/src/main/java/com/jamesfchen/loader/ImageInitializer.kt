package com.jamesfchen.loader

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class ImageInitializer : Initializer<Unit> {
    @AddTrace(name = "ImageInitializer#create",enabled = true)
    override fun create(context: Context) {
        Log.d("cjf","ImageInitializer#create")
        //        Fresco.initialize(this);
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
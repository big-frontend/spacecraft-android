package com.jamesfchen.loader

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class LoggerInitializer : Initializer<Unit> {
    @AddTrace(name = "LoggerInitializer#create",enabled = true)
    override fun create(context: Context) {
        Log.d("cjf","LoggerInitializer#create")
        Logger.addLogAdapter(object : AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder().tag(
                App.TAG
            ).build()
        ) {
            override fun isLoggable(priority: Int, tag: String): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
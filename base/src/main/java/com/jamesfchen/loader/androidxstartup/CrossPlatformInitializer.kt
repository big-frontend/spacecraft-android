package com.jamesfchen.loader.androidxstartup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace

class CrossPlatformInitializer : Initializer<Unit> {
    companion object {
        const val TAG = "StorageInitializer"
    }

    @AddTrace(name = "CrossPlatformInitializer#create", enabled = true)
    override fun create(context: Context) {
        Log.d("cjf", "CrossPlatformInitializer#create")
        //hybrid flutter react-native init

    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
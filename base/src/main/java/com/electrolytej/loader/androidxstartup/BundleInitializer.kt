package com.electrolytej.loader.androidxstartup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace


class BundleInitializer : Initializer<Unit> {
    @AddTrace(name = "RouterInitializer#create",enabled = true)
    override fun create(context: Context) {
        Log.d("cjf","RouterInitializer#create")
//        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
//            ARouter.openLog() // Print log
//            ARouter.openDebug() // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
//        }
//        ARouter.init(context as Application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
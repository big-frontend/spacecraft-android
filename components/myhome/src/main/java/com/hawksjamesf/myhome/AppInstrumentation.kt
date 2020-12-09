package com.hawksjamesf.myhome

import android.app.Instrumentation
import android.os.Bundle
import android.util.Log

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/05/2020  Sat
 */
class AppInstrumentation : Instrumentation() {
    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        Log.d("cjf","onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("cjf","onStart")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("cjf","onDestroy")
    }

}
package com.jamesfchen.main

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 10月/09/2021  周六
 */
class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("cjf", "TestActivity#onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("cjf", "TestActivity#onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("cjf", "TestActivity#onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("cjf", "TestActivity#onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("cjf", "TestActivity#onStop")
    }
}
package com.jamesfchen.loader.monitor

import android.os.Looper
import android.util.Log
import android.util.Printer
import android.view.Choreographer

class FrameTrace2 {
    companion object {
        fun start() {
            Looper.getMainLooper().setMessageLogging(MyPrint())
        }

        fun stop() {
        }
        private class MyPrint : Printer {
            override fun println(x: String?) {

            }
        }
    }
}

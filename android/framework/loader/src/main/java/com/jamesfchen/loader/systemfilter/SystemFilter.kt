package com.jamesfchen.loader.systemfilter

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View
import androidx.lifecycle.ProcessLifecycleOwner
import com.jamesfchen.loader.SApp

class SystemFilter {
    companion object {
        var isGrayMode = false
        var isDarkMode = false
        private val mGrayModePaint = Paint()
        private val mGrayModeCm = ColorMatrix()

        init {
            mGrayModeCm.setSaturation(0f)
            mGrayModePaint.colorFilter = ColorMatrixColorFilter(mGrayModeCm)
        }

        @JvmStatic
        fun init(app: SApp) {
//            ProcessLifecycleOwner.get().lifecycle.addObserver(SystemFilter())
//            app.registerActivityLifecycleCallbacks(SystemFilter())
        }

        @JvmStatic
        fun setGrayLayer(view: View) {
            if (isGrayMode) {
                view.setLayerType(View.LAYER_TYPE_HARDWARE, mGrayModePaint)
            } else {
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }
        }

        @JvmStatic
        fun applyGrayMode() {
            isGrayMode =true
            isDarkMode = false
        }
        @JvmStatic
        fun applyGrayMode(activity: Activity) {
            isGrayMode =true
            isDarkMode = false
            activity.recreate()
        }

        fun clearGrayMode(activity: Activity) {
            isGrayMode = false
            activity.recreate()
        }


        @JvmStatic
        fun applyDarkMode(activity: Activity) {
            if (isDarkMode) return
            isGrayMode = false
            isDarkMode = true
            activity.recreate()
        }
    }
}
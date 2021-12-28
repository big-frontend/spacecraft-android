package com.jamesfchen.loader.systemfilter

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ProcessLifecycleOwner
import com.jamesfchen.loader.App
import com.jamesfchen.loader.monitor.IActivityLifecycleObserver
import com.jamesfchen.loader.monitor.IAppLifecycleObserver

class SystemFilter : IAppLifecycleObserver, IActivityLifecycleObserver {
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
        fun init(app: App) {
            ProcessLifecycleOwner.get().lifecycle.addObserver(SystemFilter())
            app.registerActivityLifecycleCallbacks(SystemFilter())
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

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)

    }


}
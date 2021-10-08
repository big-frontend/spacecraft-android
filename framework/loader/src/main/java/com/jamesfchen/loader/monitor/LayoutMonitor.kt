package com.jamesfchen.loader.monitor

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.LayoutInflaterCompat
import java.lang.ref.WeakReference

/**
 * Copyright ® $ 2021
 * All right reserved.

 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 10月/08/2021  周五
 *
 *  布局加载耗时
 *  每个控件加载耗时：LayoutInflater.Factory2
 */
const val TAG_LAYOUT_MONITOR = "layout-monitor"

class LayoutMonitor : IAppLifecycleObserver, IActivityLifecycleObserver {
    val f = MyLayoutInflaterFactoryV2()
    override fun onAppCreate() {
        super.onAppCreate()

    }
    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        if (activity is AppCompatActivity){
            f.activityRef = WeakReference<AppCompatActivity>(activity)
            LayoutInflaterCompat.setFactory2(activity.layoutInflater,f)
        }else{
            Log.e(TAG_LAYOUT_MONITOR,"activity不是appcompat activity，无法hook")
        }

    }


    inner class MyLayoutInflaterFactoryV2 : LayoutInflater.Factory2 {
        lateinit var activityRef:WeakReference<AppCompatActivity>
        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            val activity = activityRef.get() ?: return null
            val start = System.currentTimeMillis()
            val view = activity.delegate.createView(parent,name, context, attrs)
            Log.d(
                TAG_LAYOUT_MONITOR,
                "onCreateView 1 parent:$parent name:$name 初始化耗时:${System.currentTimeMillis() - start}ms"
            )
            return view
        }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            Log.d(TAG_LAYOUT_MONITOR, "onCreateView 2:$name")
            return null
        }
    }
}
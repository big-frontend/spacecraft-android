package com.jamesfchen.loader

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.ArrayMap
import android.util.Log
import android.view.Choreographer
import android.view.FrameMetrics
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.core.util.TimeUtils
import com.jamesfchen.common.MessageStatic
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class Monitor(val app: Application) {
    companion object {
        private const val TAG = "app-monitor"
        fun init(app: Application) {
            Monitor(app).start()
        }
    }
    private val arrayMap = ArrayMap<ComponentName, Window.OnFrameMetricsAvailableListener>()
    private val handlerThread:HandlerThread by lazy{
        val h = HandlerThread("monitor-frame-thread")
        h.start()
        return@lazy h
    }
    private var frameMetricsHandler: Handler = Handler(handlerThread.looper)

    private class MyFrameCallback : Choreographer.FrameCallback {
        private var startTime = -1L
        private var endTime = -1L
        //有性能损耗，如果页面没有更新，vsync也会周期发送，导致doFrame不停地被调用
        override fun doFrame(frameTimeNanos: Long) {
            if (startTime == -1L) {
                startTime = frameTimeNanos
            } else {
                endTime = frameTimeNanos
//                Log.d("cjf", "${Thread.currentThread().id} postFrameCallback 耗时：${(endTime - startTime)/1000} ms")
                startTime =-1
            }
            Choreographer.getInstance().postFrameCallback(this)
        }
    }
    @SuppressLint("NewApi")
    private fun start() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "初始化失败，手机版本太小：${Build.VERSION.SDK_INT}")
            return
        }
//        Choreographer.getInstance().postFrameCallback(MyFrameCallback())
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            //前台
            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "前台Activity:${activity.localClassName}")
                var frameMetricsAvailableListener = arrayMap[activity.componentName]
                if (frameMetricsAvailableListener == null) {
                    frameMetricsAvailableListener = ActivityFrameMetric(activity.componentName)
                    arrayMap.put(activity.componentName, frameMetricsAvailableListener)
                }
                activity.window.addOnFrameMetricsAvailableListener(
                    frameMetricsAvailableListener,
                    frameMetricsHandler
                )

            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            //后台
            override fun onActivityStopped(activity: Activity) {
                Log.d(TAG, "后台Activity:${activity.localClassName}")
                activity.window.removeOnFrameMetricsAvailableListener(arrayMap[activity.componentName])
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    inner class ActivityFrameMetric(val componentName: ComponentName) : Window.OnFrameMetricsAvailableListener {

        @OptIn(ExperimentalTime::class)
        override fun onFrameMetricsAvailable(
            window: Window?,
            frameMetrics: FrameMetrics?,
            dropCountSinceLastInvocation: Int
        ) {
            val frameMetricsCopy = FrameMetrics(frameMetrics)
            var metricslog = "${componentName.shortClassName} frame metrics\n"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val firstDrawFrame =
                    frameMetricsCopy.getMetric(FrameMetrics.FIRST_DRAW_FRAME)
                metricslog += "first draw frame:${firstDrawFrame ==1L}\t"
                val intendedVsyncNs = frameMetricsCopy.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
                metricslog += "intended vsync timestamp:${convertMs(intendedVsyncNs)}ms\t"
                val vsyncNs = frameMetricsCopy.getMetric(FrameMetrics.VSYNC_TIMESTAMP)
                metricslog += "vsync timestamp:${convertMs(vsyncNs)}ms\n"
                val totalDurationNs =
                    frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION)
                metricslog += "total:${convertMs(totalDurationNs)}ms\t"
                metricslog += "drop count:${dropCountSinceLastInvocation}\n"
            }
            val inputHandlerDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.INPUT_HANDLING_DURATION)
            metricslog += "input handle:${convertMs(inputHandlerDurationNs)}ms\t"
            val animationDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.ANIMATION_DURATION)
            metricslog += "animation:${convertMs(animationDurationNs)}ms\t"
            // Layout measure duration in Nano seconds
            val layoutMeasureDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)
            metricslog += "layout/measure:${
                convertMs(
                    layoutMeasureDurationNs
                )
            }ms\t"
            val drawDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.DRAW_DURATION)
            metricslog += "draw:${convertMs(drawDurationNs)}ms\t\t"
            val syncDurationNs = frameMetricsCopy.getMetric(FrameMetrics.SYNC_DURATION)
            metricslog += "sync:${convertMs(syncDurationNs)}ms\t"
            val swapBuffersDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.SWAP_BUFFERS_DURATION)
            metricslog += "swap buffers:${convertMs(swapBuffersDurationNs)}ms\t"
            val commandIssueDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION)
            metricslog += "command issue:${convertMs(commandIssueDurationNs)}ms\t"
            val unknownDelayDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)
            metricslog += "unknow delay:${convertMs(unknownDelayDurationNs)}ms\t"
            Log.d(TAG, metricslog)
        }
        fun convertMs(timeNs: Long):Long{
            return TimeUnit.NANOSECONDS.toMillis(timeNs)
        }

    }

}
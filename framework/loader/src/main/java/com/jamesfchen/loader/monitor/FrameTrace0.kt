package com.jamesfchen.loader.monitor

import android.app.Activity
import android.content.ComponentName
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.ArrayMap
import android.util.Log
import android.view.FrameMetrics
import android.view.Window
import androidx.annotation.RequiresApi
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

@RequiresApi(Build.VERSION_CODES.N)
class FrameTrace0(val componentName: ComponentName) :
    Window.OnFrameMetricsAvailableListener {
    companion object {
        private val arrayMap = ArrayMap<ComponentName, Window.OnFrameMetricsAvailableListener>()
        private val handlerThread: HandlerThread by lazy {
            val h = HandlerThread("monitor-frame-thread")
            h.start()
            return@lazy h
        }
        private var frameMetricsHandler: Handler = Handler(handlerThread.looper)

        fun start(activity: Activity) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.e(TAG_APM_MONITOR, "初始化失败，手机版本太小：${Build.VERSION.SDK_INT}")
                return
            }
            var frameMetricsAvailableListener = arrayMap[activity.componentName]
            if (frameMetricsAvailableListener == null) {
                frameMetricsAvailableListener = FrameTrace0(activity.componentName)
                arrayMap[activity.componentName] = frameMetricsAvailableListener
            }
            activity.window.addOnFrameMetricsAvailableListener(
                frameMetricsAvailableListener,
                frameMetricsHandler
            )
        }

        fun stop(activity: Activity) {
            activity.window.removeOnFrameMetricsAvailableListener(arrayMap[activity.componentName])
        }

    }

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
            metricslog += "first draw frame:${firstDrawFrame == 1L}\t"
            val intendedVsyncNs =
                frameMetricsCopy.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
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
        Log.d(TAG_APM_MONITOR, metricslog)
    }

    fun convertMs(timeNs: Long): Long {
        return TimeUnit.NANOSECONDS.toMillis(timeNs)
    }

}
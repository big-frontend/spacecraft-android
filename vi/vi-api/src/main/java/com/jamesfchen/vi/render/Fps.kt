package com.jamesfchen.vi.render

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.os.*
import android.util.ArrayMap
import android.util.Log
import android.view.*
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import com.jamesfchen.vi.lifecycle.AbsActivitiesLifecycleObserver
import com.jamesfchen.vi.lifecycle.AbsAppLifecycleObserver
import java.util.concurrent.TimeUnit

/** fps监控方案(通过监控主线程执行耗时，当超过阈值（微信认为700ms为卡顿阈值，携程认为600ms为长卡顿还可以接受，短卡顿5次发生），dump出当前线程的执行堆栈。)
 * - 计算两次Choreographer#postFrameCallback回调方法的时间差(当UI不刷新时，postFrameCallback方法还在监听，占用了cpu资源)
 * - Looper#loop方法中dispatchMessage执行前后的Printer对象的println方法(println的实参有字符串拼接会产生大量对象造成性能损耗)
 * - android7.0+提供的监控接口Window#OnFrameMetricsAvailableListener
 *
 *  收集的帧数大于300帧时，处理300帧都是level，如果帧数消耗时间总计为10s就上报这10s内的所有帧数情况。
 */
const val TAG_FRAME_MONITOR = "fps-monitor"
@Keep
class FpsItem(val app: Application) : AbsActivitiesLifecycleObserver(), AbsAppLifecycleObserver {
    private val mFpsItem = FpsView(app)
    override fun onAppCreate() {
        mFpsItem.setVisible(true)
    }
    override fun onActivityForeground(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FrameTrace0.start(activity)
        } else {
            FrameTrace1.start(activity)
        }
    }

    override fun onActivityBackground(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FrameTrace0.stop(activity)
        } else {
            FrameTrace1.stop(activity)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    class FrameTrace0(val activity: Activity) : Window.OnFrameMetricsAvailableListener {
        companion object {
            private val arrayMap = ArrayMap<ComponentName, Window.OnFrameMetricsAvailableListener>()
            private val handlerThread: HandlerThread by lazy {
                val h = HandlerThread("monitor-frame-thread")
                h.start()
                return@lazy h
            }
            private var frameMetricsHandler: Handler = Handler(handlerThread.looper)
            fun start(activity: Activity) {
                var frameMetricsAvailableListener = arrayMap[activity.componentName]
                if (frameMetricsAvailableListener == null) {
                    frameMetricsAvailableListener = FrameTrace0(activity)
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

        override fun onFrameMetricsAvailable(
            window: Window?,
            frameMetrics: FrameMetrics?,
            dropCountSinceLastInvocation: Int
        ) {
            val frameMetricsCopy = FrameMetrics(frameMetrics)
            var metricslog = "${activity.componentName.shortClassName} frame metrics "
            val totalDurationNs = frameMetricsCopy.getMetric(FrameMetrics.TOTAL_DURATION)
            metricslog += "total:${convertMs(totalDurationNs)}ms\t"
            val firstDrawFrame = frameMetricsCopy.getMetric(FrameMetrics.FIRST_DRAW_FRAME)
            metricslog += "first draw frame:${firstDrawFrame}\t"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val deadline = frameMetricsCopy.getMetric(FrameMetrics.DEADLINE)
                metricslog += "deadline:${convertMs(deadline)}ms\t"
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intendedVsyncNs = frameMetricsCopy.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
                metricslog += "intended vsync timestamp:${convertMs(intendedVsyncNs)}ms\t"
                val vsyncNs = frameMetricsCopy.getMetric(FrameMetrics.VSYNC_TIMESTAMP)
                metricslog += "vsync timestamp:${convertMs(vsyncNs)}ms\t"
            }
            metricslog += "drop count:${dropCountSinceLastInvocation}\n"
            val inputHandlerDurationNs = frameMetricsCopy.getMetric(FrameMetrics.INPUT_HANDLING_DURATION)
            metricslog += "input handle:${convertMs(inputHandlerDurationNs)}ms\t"
            val animationDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.ANIMATION_DURATION)
            metricslog += "animation:${convertMs(animationDurationNs)}ms\t"
            // Layout measure duration in Nano seconds
            val layoutMeasureDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)
            metricslog += "layout/measure:${convertMs(layoutMeasureDurationNs)}ms\t"
            val drawDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.DRAW_DURATION)
            metricslog += "draw:${convertMs(drawDurationNs)}ms\t"
            val syncDurationNs = frameMetricsCopy.getMetric(FrameMetrics.SYNC_DURATION)
            metricslog += "sync:${convertMs(syncDurationNs)}ms\t"
            val swapBuffersDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.SWAP_BUFFERS_DURATION)
            metricslog += "swap buffers:${convertMs(swapBuffersDurationNs)}ms\t"
            val commandIssueDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.COMMAND_ISSUE_DURATION)
            metricslog += "command issue:${convertMs(commandIssueDurationNs)}ms\t"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val gpuDuration =
                    frameMetricsCopy.getMetric(FrameMetrics.GPU_DURATION)
                metricslog += "gpu duration:${convertMs(gpuDuration)}ms\t"
            }
            val unknownDelayDurationNs =
                frameMetricsCopy.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)
            metricslog += "unknown delay:${convertMs(unknownDelayDurationNs)}ms"
            Log.e(TAG_FRAME_MONITOR, metricslog)
        }

        fun convertMs(timeNs: Long): Long {
            return TimeUnit.NANOSECONDS.toMillis(timeNs)
        }

    }


    /**
     * ui没有更新监听回调不断，有性能损耗
     */
    class FrameTrace1(val activity: Activity) : Choreographer.FrameCallback,
        ViewTreeObserver.OnDrawListener {
        companion object {
            private lateinit var myFrameCallback: FrameTrace1
            fun start(activity: Activity) {
                myFrameCallback = FrameTrace1(activity)
                Choreographer.getInstance().postFrameCallback(myFrameCallback)
                activity.window.decorView.viewTreeObserver.addOnDrawListener(myFrameCallback)
            }

            fun stop(activity: Activity) {
                Choreographer.getInstance().removeFrameCallback(myFrameCallback)
                activity.window.decorView.viewTreeObserver.removeOnDrawListener(myFrameCallback)
            }
        }

        private var startTime = -1L
        private var endTime = -1L
        private var isDrawing = false

        //有性能损耗，如果页面没有更新，vsync也会周期发送，导致doFrame不停地被调用
        override fun doFrame(frameTimeNanos: Long) {
            if (isDrawing) {//计算的是上一帧的耗时
                if (startTime == -1L) {
                    startTime = frameTimeNanos
                } else {
                    endTime = frameTimeNanos
                    Log.e(
                        TAG_FRAME_MONITOR,
                        "${Thread.currentThread()} postFrameCallback 耗时：${(endTime - startTime) / 1000000f} ms"
                    )
                    startTime = -1
                }
            }
            Choreographer.getInstance().postFrameCallback(this)
            isDrawing = false
        }


        override fun onDraw() {
            isDrawing = true

        }
    }


    /**
     * 慢函数监控
     *
     * 一帧消耗700ms就说明存在慢函数，然后上报每个函数的耗时
     */
    class EvilMethodChecker {
    }
}



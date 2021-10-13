package com.jamesfchen.loader.monitor.tracer

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.os.*
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.Log
import android.util.Printer
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.jamesfchen.loader.monitor.IActivityLifecycleObserver
import com.jamesfchen.loader.monitor.IAppLifecycleObserver
import com.jamesfchen.loader.monitor.ILifecycleObserver
import com.jamesfchen.loader.monitor.MonitoredItem
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

/** fps监控方案(通过监控主线程执行耗时，当超过阈值（微信认为700ms为卡顿阈值，携程认为600ms为长卡顿还可以接受，短卡顿5次发生），dump出当前线程的执行堆栈。)
 * - 计算两次Choreographer#postFrameCallback回调方法的时间差(当UI不刷新时，postFrameCallback方法还在监听，占用了cpu资源)
 * - Looper#loop方法中dispatchMessage执行前后的Printer对象的println方法(println的实参有字符串拼接会产生大量对象造成性能损耗)
 * - android7.0+提供的监控接口Window#OnFrameMetricsAvailableListener
 *
 *  收集的帧数大于300帧时，处理300帧都是level，如果帧数消耗时间总计为10s就上报这10s内的所有帧数情况。
 */
const val TAG_FRAME_MONITOR = "fps-monitor"
@MonitoredItem
class FpsItem : ILifecycleObserver {
    override fun onActivityForeground(activity: Activity) {
        super.onActivityForeground(activity)
        FrameTrace0.start(activity)
        FrameTrace1.start(activity)
        FrameTrace2.start()
    }

    override fun onActivityBackground(activity: Activity) {
        super.onActivityBackground(activity)
        FrameTrace0.stop(activity)
        FrameTrace1.stop(activity)
        FrameTrace2.stop()
    }

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
                    Log.e(TAG_FRAME_MONITOR, "初始化失败，手机版本太小：${Build.VERSION.SDK_INT}")
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
            Log.d(TAG_FRAME_MONITOR, metricslog)
        }

        fun convertMs(timeNs: Long): Long {
            return TimeUnit.NANOSECONDS.toMillis(timeNs)
        }

    }


    /**
     * ui没有更新监听回调不断，有性能损耗
     */
    class FrameTrace1 : Choreographer.FrameCallback, ViewTreeObserver.OnDrawListener {
        companion object {
            private val myFrameCallback = FrameTrace1()
            fun start(activity: Activity) {
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

        //有性能损耗，如果页面没有更新，vsync也会周期发送，导致doFrame不停地被调用
        override fun doFrame(frameTimeNanos: Long) {
            if (isDrawing) {
                if (startTime == -1L) {
                    startTime = frameTimeNanos
                } else {
                    endTime = frameTimeNanos
                    Log.d(
                        TAG_FRAME_MONITOR,
                        "${Thread.currentThread().id} postFrameCallback 耗时：${(endTime - startTime) / 1000} ms"
                    )
                    startTime = -1
                }
            }
            Choreographer.getInstance().postFrameCallback(this)
            isDrawing = false
        }

        private var isDrawing = false
        override fun onDraw() {
            isDrawing = true

        }
    }


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
//    class FrameTrace3 {
//        companion object {
//            fun start() {
//                Looper.setObserver(MyObserver())
//            }
//
//            fun stop() {
//            }
//
//           private class MyObserver :Looper.Observer{}
//        }
//    }
    /**
     * 慢函数监控
     *
     * 一帧消耗700ms就说明存在慢函数，然后上报每个函数的耗时
     */
    class EvilMethodChecker {
    }
}


/**
 *
 *  布局加载耗时
 *  每个控件加载耗时：LayoutInflater.Factory2
 */
const val TAG_LAYOUT_MONITOR = "layoutinflater-monitor"
@MonitoredItem
class LayoutInflateItem : IAppLifecycleObserver, IActivityLifecycleObserver {
    val f = MyLayoutInflaterFactoryV2()
    override fun onAppCreate() {
        super.onAppCreate()

    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        if (activity is AppCompatActivity) {
            f.activityRef = WeakReference<AppCompatActivity>(activity)
            LayoutInflaterCompat.setFactory2(activity.layoutInflater, f)
        } else {
            Log.e(TAG_LAYOUT_MONITOR, "activity不是appcompat activity，无法hook")
        }

    }


    inner class MyLayoutInflaterFactoryV2 : LayoutInflater.Factory2 {
        lateinit var activityRef: WeakReference<AppCompatActivity>
        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            val activity = activityRef.get() ?: return null
            val start = System.currentTimeMillis()
            val view = activity.delegate.createView(parent, name, context, attrs)
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

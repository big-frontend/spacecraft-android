package com.jamesfchen.vi.util

import android.app.Activity
import android.view.ViewTreeObserver

/**
 * 渲染阶段：
 * - 处理 input
 * - 处理 animate
 * - 处理 view tree:measure layout draw
 */
/**
 * view post的任务会在 view被attach到window时 post到MQ
 */
fun Activity.firstRender(onFirstRenderStart: (ts: Long) -> Unit,onFirstRenderEnd: (ts: Long) -> Unit) {
    val start = System.currentTimeMillis()
    window.decorView.post { onFirstRenderEnd(System.currentTimeMillis() - start) }
}

/**
 * ViewRootImpl 在处理 pre draw时，默认cancelDraw为false，表示当前帧经过测量布局之后开始绘制
 */
fun Activity.firstMeasureAndLayout(onFirstMeasureAndLayoutStart: (ts: Long) -> Unit,onFirstMeasureAndLayoutEnd: (ts: Long) -> Unit) {
    val start = System.currentTimeMillis()
    window.decorView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
            onFirstMeasureAndLayoutEnd(System.currentTimeMillis() - start)
            return false//cancelDraw
        }
    })
}

fun Activity.onDraw(onDraw: () -> Unit) {
    window.decorView.viewTreeObserver.addOnDrawListener (object : ViewTreeObserver.OnDrawListener {
        override fun onDraw() {
            window.decorView.viewTreeObserver.removeOnDrawListener(this)
            onDraw()
        }
    })
}

fun Activity.onLayout(onLayout: () -> Unit) {
    window.decorView.viewTreeObserver.addOnGlobalLayoutListener  (object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            onLayout()
        }
    })
}
fun Activity.onWindowFocusChanged(onWindowFocusChanged: (Boolean) -> Unit) {
    window.decorView.viewTreeObserver.addOnWindowFocusChangeListener(object :ViewTreeObserver.OnWindowFocusChangeListener{
        override fun onWindowFocusChanged(hasFocus: Boolean) {
            window.decorView.viewTreeObserver.removeOnWindowFocusChangeListener(this)
            onWindowFocusChanged(hasFocus)
        }
    })
}

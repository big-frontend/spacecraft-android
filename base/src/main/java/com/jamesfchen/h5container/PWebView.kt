package com.jamesfchen.h5container

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.webkit.WebView


/**
 * https://blog.csdn.net/Luoshengyang/article/details/53366272
 */
class PWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : WebView(
    context,
    attrs,
    defStyleAttr
) {
    private lateinit var mBaseGLRenderer: BaseGLRenderer

    /**
     * 在App渲染UI的第一阶段，Android WebView的成员函数onDraw会被调用。
     * 从前面Android WebView执行GPU命令的过程分析一文又可以知道，Android WebView在Native层有一个BrowserViewRenderer对象。
     * 当Android WebView的成员函数onDraw被调用时，并且App的UI以硬件加速方式渲染时，
     * 这个Native层BrowserViewRenderer对象的成员函数OnDrawHardware会被调用
     */
    override fun onDraw(canvas: Canvas) {
        //returns canvas attached to gl texture to draw on
        val glAttachedCanvas: Canvas = mBaseGLRenderer.onDrawViewBegin()
        if (glAttachedCanvas != null) {
            //translate canvas to reflect view scrolling
            val xScale = glAttachedCanvas.width / canvas.width.toFloat()
            glAttachedCanvas.scale(xScale, xScale)
            glAttachedCanvas.translate(-scrollX.toFloat(), -scrollY.toFloat())
            //draw the view to provided canvas
            super.onDraw(glAttachedCanvas)
        }
        // notify the canvas is updated
        mBaseGLRenderer.onDrawViewEnd()
    }

    fun setViewToGLRenderer(viewTOGLRenderer: BaseGLRenderer) {
        mBaseGLRenderer = viewTOGLRenderer
    }
}

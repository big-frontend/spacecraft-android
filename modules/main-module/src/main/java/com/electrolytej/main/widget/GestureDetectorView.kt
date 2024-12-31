package com.electrolytej.main.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.electrolytej.widget.multitouch.MoveGestureDetector
import kotlin.math.abs
import kotlin.math.atan2

class GestureDetectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr), GestureDetector.OnGestureListener,
    MoveGestureDetector.OnMoveGestureListener {
    companion object {
        private val TAG = GestureDetectorView::class.java.simpleName
    }

    private val slideDistance = 30
    private val slideAngle = 45
    private var mMoveGestureDetector: MoveGestureDetector = MoveGestureDetector(context, this)

    //    private var mGestureDetector: GestureDetector
    private var gestureListener: IGestureListener? = null

    init {
//        mGestureDetector = GestureDetector(context, this)
//        setOnTouchListener { v, ev ->
//            mGestureDetector.onTouchEvent(ev)
//            mMoveGestureDetector.onTouchEvent(ev)
//            return@setOnTouchListener true
//        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    private var x1 = 0f
    private var y1 = 0f
    private var hit = false
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var deltaX = 0f
        var deltaY = 0f
        var x2 = 0f
        var y2 = 0f
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            x1 = ev.x
            y1 = ev.y
        } else if (ev?.action == MotionEvent.ACTION_MOVE) {
            x2 = ev.x
            y2 = ev.y
            deltaX = x2 - x1
            deltaY = y2 - y1
            val angleY = atan2(abs(deltaX), abs(deltaY)) * (180 / Math.PI)
            Log.d(TAG, ">>>onInterceptTouchEvent delta:${deltaX},${deltaY}, point1:(${x1},${y1}) point2:(${x2},${y2}) ${angleY}/${slideAngle}")
            if (angleY >= slideAngle) {
                //父节点不要拦截,不能滑动
                parent.requestDisallowInterceptTouchEvent(true)
                hit = true
                return true
            }

        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        mGestureDetector.onTouchEvent(ev)
//        mMoveGestureDetector.onTouchEvent(ev)
        if (ev?.action == MotionEvent.ACTION_UP) {
            var x2 = ev.x
            var y2 = ev.y
            var deltaX = x2 - x1
            var deltaY = y2 - y1
            if (abs(deltaX) >= slideDistance && hit) {
                Log.d(TAG, "滑动跳转成功")
                //父节点要拦截
                parent.requestDisallowInterceptTouchEvent(false)
                hit = false
                return true
            } else {
                return super.onTouchEvent(ev)
            }
        }
        return true
    }

    override fun onDown(e: MotionEvent): Boolean {
//        Log.d(TAG, "onDown  ${e.x}/$slideDistance ${e.x}/${e.y}")
        return false
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (e1 == null) {
            return false
        }
        val deltaX = e2.x - e1.x
        val deltaY = e2.y - e1.y
        val angleY = atan2(abs(deltaX), abs(deltaY)) * (180 / Math.PI)
        Log.d(
            TAG,
            "onScroll  angle:${angleY}/${slideAngle} distance:$deltaX/$slideDistance  e1:${e1.x}/${e1.y} e2:${e2.x}/${e2.y} delta:$deltaX/$deltaY"
        )
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 == null) {
            return false
        }
        val deltaX = e2.x - e1.x
        val deltaY = e2.y - e1.y
        val angleY = atan2(abs(deltaX), abs(deltaY)) * (180 / Math.PI)
        Log.d(
            TAG,
            "onFling  angle:${angleY}/${slideAngle} distance:$deltaX/$slideDistance  e1:${e1.x}/${e1.y} e2:${e2.x}/${e2.y} delta:$deltaX/$deltaY"
        )
        return true
    }

    private var angle = false
    override fun onMoveBegin(detector: MoveGestureDetector): Boolean {
        Log.d(
            TAG,
            ">>>onMoveBegin ${detector.focusX}/${detector.focusY} delta:${detector.focusDelta}"
        )
        return true
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {
        //最终点和起点的delta
        val deltaX = detector.focusDelta.x.toDouble()
        val deltaY = detector.focusDelta.y.toDouble()
        Log.d(
            TAG,
            "<<<onMoveEnd distance:$deltaX/$slideDistance $angle ${detector.focusX}/${detector.focusY} delta:${detector.focusDelta}"
        )
        if (abs(deltaX) >= slideDistance && angle) {
            Log.d(TAG, "跳转成功")
            gestureListener?.onMoveEnd()
        } else {
//            Log.d(TAG, "跳转成功")
        }
        //父节点要拦截
        parent.requestDisallowInterceptTouchEvent(false)
        angle = false

    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
        val deltaX = detector.focusDelta.x.toDouble()
        val deltaY = detector.focusDelta.y.toDouble()
//        val deltaX = detector.focusX - startX
//        val deltaY = detector.focusY - startY
        //与 y 轴夹角
        val angleY = atan2(abs(deltaX), abs(deltaY)) * (180 / Math.PI)
        Log.d(
            TAG,
            "onMove  angle:${angleY}/${slideAngle} distance:$deltaX/$slideDistance  ${detector.focusX}/${detector.focusY} delta:${detector.focusDelta} $deltaX/$deltaY"
        )
        if (angleY >= slideAngle) {
            //父节点不要拦截
            parent.requestDisallowInterceptTouchEvent(true)
            angle = true
        }
        return false
    }

    fun isClick(targetView: View?, event: MotionEvent): Boolean {
        if (targetView == null) {
            return false
        }
        val x = event.x
        val y = event.y
        return viewContains(targetView, x, y)

    }

    //判断是否处于区域内。
    fun viewContains(view: View, x: Float, y: Float): Boolean {
        return view.left < view.right && view.top < view.bottom && x >= view.left && x < view.right && y >= view.top && y < view.bottom
    }

    fun setOnGestureListener(l: IGestureListener) {
        gestureListener = l
    }

    interface IGestureListener {
        fun onMoveEnd()
    }

}
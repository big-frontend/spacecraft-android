package com.jamesfchen.vi.startup

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import com.jamesfchen.vi.DebugOverlayController
import com.jamesfchen.vi.R
import com.jamesfchen.vi.WindowOverlayCompat
import com.jamesfchen.vi.util.UiThreadUtil

class StartupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1, defStyleRes: Int = -1
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        private const val UPDATE_INTERVAL_MS = 500L
    }

    private val mMonitorRunnable: MonitorRunnable = MonitorRunnable()
    private var mTextView: TextView
    private val mWindowManager: WindowManager
    private var txt: String = ""

    init {
        inflate(context, R.layout.fps_view, this)
        mTextView = findViewById<View>(R.id.fps_text) as TextView
        mTextView.gravity = Gravity.TOP or Gravity.LEFT
        (mTextView.layoutParams as LayoutParams).gravity = Gravity.TOP or Gravity.LEFT
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mMonitorRunnable.start()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mMonitorRunnable.stop()
    }

    inner class MonitorRunnable : Runnable {
        var mShouldStop = false
        override fun run() {
            if (mShouldStop) return
            mTextView.text = txt
            postDelayed(this, UPDATE_INTERVAL_MS)
        }

        fun start() {
            mShouldStop = false
            this@StartupView.post(this)
        }

        fun stop() {
            mShouldStop = true
        }
    }

    fun setText(s: String) {
        txt = s
    }
    fun setVisible(visible: Boolean) {
        UiThreadUtil.runOnUiThread(Runnable {
            if (visible) {
                if (!DebugOverlayController.permissionCheck(context)) {
                    Log.d(TAG_STARTUP_MONITOR, "Wait for overlay permission to be set")
                    return@Runnable
                }
                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowOverlayCompat.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    PixelFormat.TRANSLUCENT
                )
                params.gravity = Gravity.TOP or Gravity.LEFT
                mWindowManager.addView(this, params)
            } else if (!visible) {
                removeAllViews()
                mWindowManager.removeView(this)
            }
        })
    }


}
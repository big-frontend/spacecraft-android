package com.jamesfchen.myhome.widget.carousel

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.View.OnTouchListener
import androidx.viewpager.widget.ViewPager
import android.widget.TextView
import android.os.Looper
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.jamesfchen.myhome.R
import java.lang.ref.WeakReference

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Feb/16/2019  Sat
 */
class CarouselView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr), OnPageChangeListener, OnTouchListener {
    companion object {
        private val TAG = CarouselView::class.java.simpleName
        private var sInterval = 0
        private const val DEFAULT_INTERVAL = 3500
        private var dragging = false
    }

    internal class H(vp: ViewPager) : Handler(Looper.getMainLooper()) {
        var vpRef: WeakReference<ViewPager> = WeakReference(vp)
        override fun handleMessage(msg: Message) {
            val vp = vpRef.get()
            if (!dragging && vp != null) {
                val currentItem = vp.currentItem
                vp.setCurrentItem(currentItem + 1, true)
                sendEmptyMessageDelayed(0, sInterval.toLong())
            }
        }

    }

    private var mAutoStart = false
    private var mVpContent: ViewPager
    private var mTvIndicator: TextView
    private lateinit var mPagerAdapter: CarouselPagerAdapter<*>
    private var mCurPosition = 0
    private val transformer: ViewPager.PageTransformer? = null
    private var mHandler: Handler

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CarouselView)
        mAutoStart = typedArray.getBoolean(R.styleable.CarouselView_cv_autoStart, true)
        sInterval = typedArray.getInteger(R.styleable.CarouselView_cv_interval, DEFAULT_INTERVAL)
        typedArray.recycle()
        val view = inflate(context, R.layout.view_carousel, this)
        mVpContent = view.findViewById(R.id.vp_content)
        mHandler = H(mVpContent)
        mTvIndicator = view.findViewById(R.id.tv_indicator)
        mVpContent.offscreenPageLimit = 3
        if (mAutoStart) {
            mVpContent.setOnTouchListener(this)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mAutoStart && mPagerAdapter.pagers != 0) {
            mHandler.sendEmptyMessageDelayed(0, sInterval.toLong())
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            mHandler.sendEmptyMessageDelayed(0, sInterval.toLong())
        } else {
            mHandler.removeMessages(0)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeMessages(0)
    }

    fun setAdapter(adapter: CarouselPagerAdapter<*>) {
        mPagerAdapter = adapter
        mPagerAdapter.setViewPager(mVpContent)
        mPagerAdapter.setIndicator(mTvIndicator)
        mVpContent.adapter = mPagerAdapter
        mVpContent.setCurrentItem(0, false)
        mTvIndicator.text =  "1/" + mPagerAdapter.pagers
        mVpContent.addOnPageChangeListener(this)
    }

    fun setPageTransformer(pageTransformer: ViewPager.PageTransformer) {
        mVpContent.setPageTransformer(true, pageTransformer)
    }

    fun setCurrentItem(position: Int, smoothScroll: Boolean) {
        mVpContent.setCurrentItem(position, smoothScroll)
    }

    val currentItem: Int
        get() = mVpContent.currentItem

    override fun onPageSelected(fakePosition: Int) {
        if (mPagerAdapter.pagers != 0) {
            mCurPosition = fakePosition % mPagerAdapter.pagers
            Log.d(TAG, "fakePosition" + fakePosition + "_cur position:" + mCurPosition)
            mTvIndicator.text =
                "${(fakePosition % mPagerAdapter.pagers)+1}/${mPagerAdapter.pagers}"
        }
    }

    override fun onPageScrolled(
        fakePosition: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d(TAG, "state:$state")
        when (state) {
            ViewPager.SCROLL_STATE_IDLE -> {
            }
            ViewPager.SCROLL_STATE_DRAGGING -> {
            }
            ViewPager.SCROLL_STATE_SETTLING -> {
            }
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE) {
            dragging = true
            mHandler.removeMessages(0)
        } else if (motionEvent.action == MotionEvent.ACTION_UP) {
            dragging = false
            mHandler.sendEmptyMessageDelayed(0, sInterval.toLong())
        }
        return false
    }


}
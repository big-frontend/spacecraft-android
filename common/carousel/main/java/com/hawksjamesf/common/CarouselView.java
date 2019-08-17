package com.hawksjamesf.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hawksjamesf.common.adapter.CarouselPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class CarouselView extends FrameLayout implements ViewPager.OnPageChangeListener, ViewPager.OnTouchListener {
    private static final String TAG = CarouselView.class.getSimpleName();
    private boolean mAutoStart;
    private int mInterval;
    private static int DEFAULT_INTERVAL = 3500;
    private ViewPager mVpContent;
    private TextView mTvIndicator;
    private CarouselPagerAdapter mPagerAdapter;
    private boolean dragging;
    private int mCurPosition = 0;
    private ViewPager.PageTransformer transformer;
    private TextureView mTextureView;

    public CarouselView(@NonNull Context context) {
        super(context);
        initView(null, 0);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!dragging) {
                int currentItem = mVpContent.getCurrentItem();
                mVpContent.setCurrentItem(currentItem + 1, true);
                sendEmptyMessageDelayed(0, mInterval);
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
        if (mAutoStart && mPagerAdapter != null && mPagerAdapter.getPagers() != 0) {
            mHandler.sendEmptyMessageDelayed(0, mInterval);
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "onWindowFocusChanged:" + hasWindowFocus);
        if (hasWindowFocus) {
            mHandler.sendEmptyMessageDelayed(0, mInterval);
        } else {
            mHandler.removeMessages(0);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
        mHandler.removeMessages(0);
    }

    private void initView(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CarouselView, defStyle, 0);
        mAutoStart = typedArray.getBoolean(R.styleable.CarouselView_cv_autoStart, true);
        mInterval = typedArray.getInteger(R.styleable.CarouselView_cv_interval, DEFAULT_INTERVAL);
        typedArray.recycle();
        View view = inflate(getContext(), R.layout.view_carousel, this);
        mVpContent = view.findViewById(R.id.vp_content);
        mTvIndicator = view.findViewById(R.id.tv_indicator);
        mVpContent.setOffscreenPageLimit(3);
        if (mAutoStart) {
            mVpContent.setOnTouchListener(this);
        }

    }

    public void setAdapter(@NonNull final CarouselPagerAdapter adapter) {
        mPagerAdapter = adapter;
        mPagerAdapter.setViewPager(mVpContent);
        mPagerAdapter.setIndicator(mTvIndicator);
        mVpContent.setAdapter(mPagerAdapter);
        mVpContent.setCurrentItem(0, false);
        mTvIndicator.setText(1 + "/" + mPagerAdapter.getPagers());
        mVpContent.addOnPageChangeListener(this);
    }

    public void setPageTransformer(@NonNull ViewPager.PageTransformer pageTransformer) {
        mVpContent.setPageTransformer(true, pageTransformer);
    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        mVpContent.setCurrentItem(position, smoothScroll);
    }

    public int getCurrentItem() {
        return mVpContent.getCurrentItem();
    }



    @Override
    public void onPageSelected(int fakePosition) {
        if (mPagerAdapter != null && mPagerAdapter.getPagers() != 0) {
            mCurPosition = fakePosition % mPagerAdapter.getPagers();
            Log.d(TAG, "fakePosition" + fakePosition + "_cur position:" + mCurPosition);
            mTvIndicator.setText(fakePosition % mPagerAdapter.getPagers() + 1 + "/" + mPagerAdapter.getPagers());
        }
    }

    @Override
    public void onPageScrolled(int fakePosition, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "state:" + state);
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: {//idle状态
            }
            case ViewPager.SCROLL_STATE_DRAGGING: {//手动滑动
//                        dragging = true;
//                        mHandler.removeMessages(0);
            }
            case ViewPager.SCROLL_STATE_SETTLING: {
                //Indicates that the pager is in the process of settling to a final position.

            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            dragging = true;
            mHandler.removeMessages(0);

        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            dragging = false;
            mHandler.sendEmptyMessageDelayed(0, mInterval);
        }

        return false;
    }

}

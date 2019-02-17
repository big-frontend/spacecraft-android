package com.hawksjamesf.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hawksjamesf.common.transformer.DepthPageTransformer;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jf.chen
 * @email: jf.chen@Ctrip.com
 * @since: Feb/16/2019  Sat
 */
public class CarouselView extends FrameLayout {
    private boolean autoStart;
    private int interval;
    private static int DEFAULT_INTERVAL = 5000;
    private ViewPager mVpContent;
    private TextView mTvIndicator;
    private PagerAdapter mPagerAdapter;
    private boolean dragging;
    private static int FAKE_SIZE = 1000;
    private Timer timer = new Timer();
    private int mDelay = 5000;
    private int mCurPosition;

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

    private void initView(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CarouselView, defStyle, 0);
        autoStart = typedArray.getBoolean(R.styleable.CarouselView_autoStart, true);
        interval = typedArray.getInteger(R.styleable.CarouselView_carouselInterval, DEFAULT_INTERVAL);
        typedArray.recycle();
        View view = inflate(getContext(), R.layout.view_carousel, this);
        mVpContent = view.findViewById(R.id.vp_content);
        mVpContent.setPageTransformer(true, new DepthPageTransformer());
        mTvIndicator = view.findViewById(R.id.tv_indicator);
        mVpContent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    dragging = true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    dragging = false;
                }
                return false;
            }
        });
        if (autoStart) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!dragging) {
                        mCurPosition += 1;
                        mVpContent.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mCurPosition == 0) {
                                    mVpContent.setCurrentItem(mCurPosition, false);
                                } else {
                                    mVpContent.setCurrentItem(mCurPosition, true);

                                }
                            }
                        });
                    }

                }
            }, mDelay, interval);
        }
//        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    public void setAdapter(@NonNull final CarouselPagerAdapter adapter) {
        mPagerAdapter = adapter;
        adapter.setViewPager(mVpContent);
        mVpContent.setAdapter(adapter);
        mTvIndicator.setText(1 + "/" + adapter.getPagers());
        mVpContent.setCurrentItem(0);
//            throw new IllegalArgumentException("Adapter is not null");
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int fakePosition) {
                mCurPosition = fakePosition % adapter.getPagers();
                mTvIndicator.setText(mCurPosition + 1 + "/" + adapter.getPagers());
            }
        });

    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        mVpContent.setCurrentItem(position, smoothScroll);
    }

    public int getCurrentItem() {
        return mVpContent.getCurrentItem();
    }

    public abstract static class CarouselPagerAdapter extends PagerAdapter {
        private ViewPager mVpContent;

        @Override
        public int getCount() {
            return FAKE_SIZE;
        }

        protected abstract int getPagers();

        protected abstract Object instantiatePager(@NonNull ViewGroup container, int position);

        protected abstract void destroyPager(@NonNull ViewGroup container, int position, @NonNull Object object);

        @CallSuper
        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            if (mVpContent != null) {
                int position = mVpContent.getCurrentItem();
                if (position == 0) {
                    position = getPagers();
                    mVpContent.setCurrentItem(position, false);
                } else if (position == FAKE_SIZE - 1) {
                    position = getPagers() - 1;
                    mVpContent.setCurrentItem(position, false);
                }
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position %= getPagers();
            return instantiatePager(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            position %= getPagers();
            destroyPager(container, position, object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }

        private void setViewPager(@NonNull ViewPager viewPager) {
            mVpContent = viewPager;
        }

    }

    public abstract static class CarouselFragmentPagerAdapter extends FragmentPagerAdapter {

        public CarouselFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}

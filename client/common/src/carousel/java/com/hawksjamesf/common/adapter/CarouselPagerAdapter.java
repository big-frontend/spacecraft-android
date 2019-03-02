package com.hawksjamesf.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawksjamesf.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/20/2019  Wed
 */
public abstract class CarouselPagerAdapter<T> extends PagerAdapter {
    public static int FAKE_SIZE = 500;
    private ViewPager mVpContent;
    private TextView mTvIndicator;
    protected List<T> dataList = new ArrayList<>();
    private List<View> mScrapViews = new ArrayList<>();

    @Nullable
    private View getScrapView() {
        if (mScrapViews.size() > 0) {
            return mScrapViews.remove(0);
        } else {
            return null;
        }
    }

    private void recycleView(View itemView) {
        mScrapViews.add(itemView);
    }

    @Override
    public int getCount() {
        return FAKE_SIZE;
    }

    public abstract int getPagers();

    @CallSuper
    public void setDataList(@NonNull List<T> dataList) {
        if (CollectionUtil.isNotEmpty(dataList)) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            int currentItem = mVpContent.getCurrentItem();
            int size = dataList.size();
//            mTvIndicator.setText( currentItem % size+ "/" + size);
            notifyDataSetChanged();
        }
    }

    @Nullable
    public T getDataForPosition(@IntRange(from = 0) int position) {
        int size = dataList.size();
        if (0 <= position && position <= size - 1) {
            return dataList.get(position);
        } else {
            return null;
        }
    }

    /**
     * fix bug:Viewpager adapter invalidate
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

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
                if (position < 0) position = 0;
                mVpContent.setCurrentItem(position, false);
            }
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (getPagers() != 0) {
            position %= getPagers();
        } else {
            position = 0;
        }
        return instantiatePager(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (getPagers() != 0) {
            position %= getPagers();
        } else {
            position = 0;
        }
        destroyPager(container, position, object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    public void setViewPager(@NonNull ViewPager viewPager) {
        mVpContent = viewPager;
    }

    public void setIndicator(@NonNull TextView indicator) {
        mTvIndicator = indicator;
    }

}

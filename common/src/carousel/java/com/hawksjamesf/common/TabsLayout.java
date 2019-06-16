package com.hawksjamesf.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.hawksjamesf.common.listener.TabsBehavior;
import com.hawksjamesf.common.util.CollectionUtil;
import com.hawksjamesf.common.util.ConvertUtil;
import com.hawksjamesf.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/26/2019  Sun
 */
@CoordinatorLayout.DefaultBehavior(TabsBehavior.class)
public class TabsLayout extends HorizontalScrollView {
    public static final int GAP = ConvertUtil.px2dp(24);

    public TabsLayout(Context context) {
        this(context, null);
    }

    public TabsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    LinearLayout llContainer;
    View indicator;
    int tabIndicatorAnimationDuration = 200;

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        View rootView = View.inflate(context, R.layout.view_tabs_layout, this);
        llContainer = rootView.findViewById(R.id.ll_container);
        indicator = rootView.findViewById(R.id.indicator);
        setHorizontalScrollBarEnabled(false);
    }


    public static class TabItem {
        public String name;
        public Drawable drawable;
        public String url;

        public TabItem(String name, Drawable drawable) {
            this.name = name;
            this.drawable = drawable;
        }

        public TabItem(String name, @DrawableRes int resId) {
            this.name = name;
            this.drawable = ContextCompat.getDrawable(Util.getApp(), resId);
        }

        public TabItem(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public void setDataList(List<TabItem> tabList) {
        if (CollectionUtil.isEmpty(tabList)) return;
        llContainer.removeAllViews();
        for (int i = 0; i < tabList.size(); ++i) {
            final View itemView = View.inflate(getContext(), R.layout.item_icon_and_text, null);

            final int finalI = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (OnTabSelectedListener l : mOnTabSelectedListenerList) {
                        if (l != null) {
                            l.onTabSelected(itemView, finalI);
                        }
                    }
                    animateIndicatorToPosition(
                            finalI, tabIndicatorAnimationDuration);
                }
            });
            TabItem tabItem = tabList.get(i);
            TextView tvName = itemView.findViewById(R.id.tv_name);
            tvName.setText(tabItem.name);
            ImageView ivIcon = itemView.findViewById(R.id.iv_icon);
            if (tabItem.drawable != null) {
                ivIcon.setImageDrawable(tabItem.drawable);
            }

            if (tabItem.url != null && tabItem.url.length() > 0) {
                Glide.with(ivIcon.getContext())
                        .load(tabItem.url)
                        .into(ivIcon);
            }
            MarginLayoutParams layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = GAP;
            llContainer.addView(itemView, layoutParams);
            if (llContainer.getChildCount() > 0) {
                final ViewGroup.LayoutParams indicatorLp = indicator.getLayoutParams();
                llContainer.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        indicatorLp.width = llContainer.getChildAt(0).getWidth();
                        indicator.setLayoutParams(indicatorLp);
                    }
                });
            }
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(View view, int position);
    }

    private List<OnTabSelectedListener> mOnTabSelectedListenerList;

    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        if (mOnTabSelectedListenerList == null) {
            mOnTabSelectedListenerList = new ArrayList<>();
        }
        mOnTabSelectedListenerList.add(onTabSelectedListener);
    }


    int curPosition;
    private ValueAnimator indicatorAnimator;

    public void animateIndicatorToPosition(final int position, int duration) {
        if (indicatorAnimator != null && indicatorAnimator.isRunning()) {
            indicatorAnimator.cancel();
            indicatorAnimator.removeAllListeners();
        }

        final View curView = llContainer.getChildAt(curPosition);
        final View targetView = llContainer.getChildAt(position);
        Log.d("TabsLayout", "animateIndicatorToPosition:" + curView + "/" + targetView + "--" + position + "/" + curPosition);
        if (targetView == null || curView == null || position == curPosition) return;

        int curLeft = curView.getLeft();
        int curRight = curView.getRight();
        int targetLeft = targetView.getLeft();
        int targetRight = targetView.getRight();

        if (targetRight > ScreenUtils.getScreenWidth()) {
            int dx = targetRight - ScreenUtils.getScreenWidth();
            smoothScrollBy(dx, 0);
        }

        indicatorAnimator = ObjectAnimator.ofFloat(indicator, "translationX", curLeft, targetLeft);
        indicatorAnimator.setInterpolator(new FastOutSlowInInterpolator());
        indicatorAnimator.setDuration(duration);
        indicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        indicatorAnimator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        curPosition = position;
                    }
                });
        indicatorAnimator.start();
    }
}
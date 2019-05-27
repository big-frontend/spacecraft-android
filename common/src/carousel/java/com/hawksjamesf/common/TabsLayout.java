package com.hawksjamesf.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.material.animation.AnimationUtils;
import com.hawksjamesf.common.listener.TabsBehavior;

import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
    List<String> tabList = new ArrayList<String>() {
        {
            add("美食");
            add("美食");
            add("美食");
            add("美食");
            add("美食");
        }
    };

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
        for (int i = 0; i < tabList.size(); ++i) {
            final View itemView = View.inflate(context, R.layout.item_icon_and_text, null);
            final int finalI = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnTabSelectedListener != null) {
                        mOnTabSelectedListener.onTabSelected(itemView, finalI);
                    }
                    animateIndicatorToPosition(
                            finalI, tabIndicatorAnimationDuration);
                }
            });
            TextView tvName = itemView.findViewById(R.id.tv_name);
            tvName.setText(tabList.get(i) + "" + i);
            llContainer.addView(itemView);
        }
        indicator = rootView.findViewById(R.id.indicator);
        final ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
        if (llContainer.getChildCount() > 0) {
            llContainer.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    layoutParams.width = llContainer.getChildAt(0).getWidth();
                    indicator.setLayoutParams(layoutParams);
                }
            });
        }
        setHorizontalScrollBarEnabled(false);
    }


    public interface OnTabSelectedListener {
        void onTabSelected(View view, int position);
    }

    private OnTabSelectedListener mOnTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener;
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
        indicatorAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
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

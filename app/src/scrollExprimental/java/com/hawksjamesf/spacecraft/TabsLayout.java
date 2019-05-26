package com.hawksjamesf.spacecraft;

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

import com.google.android.material.animation.AnimationUtils;
import com.hawksjamesf.spacecraft.listener.TabsBehavior;

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
//                    setIndicatorPositionFromTabPosition(finalI, 0f);

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

    int selectedPosition = -1;
    float selectionOffset;
    private ValueAnimator indicatorAnimator;
    boolean tabIndicatorFullWidth;
    private int indicatorLeft = -1;
    private int indicatorRight = -1;

    public interface OnTabSelectedListener {
        void onTabSelected(View view, int position);
    }

    private OnTabSelectedListener mOnTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener;
    }


    int curPosition;

    public void animateIndicatorToPosition(final int position, int duration) {
        if (indicatorAnimator != null && indicatorAnimator.isRunning()) {
            indicatorAnimator.cancel();
            indicatorAnimator.removeAllListeners();
        }

        final View curView = llContainer.getChildAt(curPosition);
        final View targetView = llContainer.getChildAt(position);
        Log.d("TabsLayout", "animateIndicatorToPosition:" + curView + "/" + targetView+"--"+position+"/"+curPosition);
        if (targetView == null || curView == null || position == curPosition) return;

        int curLeft = curView.getLeft();
        int curRight = curView.getRight();
        int targetLeft = targetView.getLeft();
        int targetRight = targetView.getRight();

        indicatorAnimator = ObjectAnimator.ofFloat(indicator, "translationX", curLeft, targetLeft);
        indicatorAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        indicatorAnimator.setDuration(duration);
        indicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("TabsLayout", "value:" + animation.getValues());
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
//    void animateIndicatorToPosition(final int position, int duration) {
//        if (indicatorAnimator != null && indicatorAnimator.isRunning()) {
//            indicatorAnimator.cancel();
//        }
//
//        final View targetView = llContainer.getChildAt(position);
//        if (targetView == null) {
//            // If we don't have a view, just update the position now and return
////            updateIndicatorPosition();
//            return;
//        }
//
//        int targetLeft = targetView.getLeft();
//        int targetRight = targetView.getRight();
//
////        if (!tabIndicatorFullWidth && targetView instanceof TabView) {
////            calculateTabViewContentBounds((TabView) targetView, tabViewContentBounds);
////            targetLeft = (int) tabViewContentBounds.left;
////            targetRight = (int) tabViewContentBounds.right;
////        }
//
//        final int finalTargetLeft = targetLeft;
//        final int finalTargetRight = targetRight;
//
//        final int startLeft = indicatorLeft;
//        final int startRight = indicatorRight;
//
//        if (startLeft != finalTargetLeft || startRight != finalTargetRight) {
//            ValueAnimator animator = indicatorAnimator = new ValueAnimator();
//            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
//            animator.setDuration(duration);
//            animator.setFloatValues(0, 1);
//            animator.addUpdateListener(
//                    new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animator) {
//                            final float fraction = animator.getAnimatedFraction();
//                            setIndicatorPosition(
//                                    AnimationUtils.lerp(startLeft, finalTargetLeft, fraction),
//                                    AnimationUtils.lerp(startRight, finalTargetRight, fraction));
//                        }
//                    });
//            animator.addListener(
//                    new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animator) {
//                            selectedPosition = position;
//                            selectionOffset = 0f;
//                        }
//                    });
//            animator.start();
//        }
//    }
//
//    void setIndicatorPosition(int left, int right) {
//        if (left != indicatorLeft || right != indicatorRight) {
//            // If the indicator's left/right has changed, invalidate
//            indicatorLeft = left;
//            indicatorRight = right;
//            ViewCompat.postInvalidateOnAnimation(llContainer);
//        }
//    }

//    void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
//        if (indicatorAnimator != null && indicatorAnimator.isRunning()) {
//            indicatorAnimator.cancel();
//        }
//
//        selectedPosition = position;
//        selectionOffset = positionOffset;
//        updateIndicatorPosition();
//    }
//
//    private void updateIndicatorPosition() {
//        final View selectedTitle = llContainer.getChildAt(selectedPosition);
//        int left;
//        int right;
////
//        if (selectedTitle != null && selectedTitle.getWidth() > 0) {
//            left = selectedTitle.getLeft();
//            right = selectedTitle.getRight();
//
//            if (!tabIndicatorFullWidth && selectedTitle instanceof LinearLayout) {
//                calculateTabViewContentBounds((TabView) selectedTitle, tabViewContentBounds);
//                left = (int) tabViewContentBounds.left;
//                right = (int) tabViewContentBounds.right;
//            }
//
//            if (selectionOffset > 0f && selectedPosition < getChildCount() - 1) {
//                // Draw the selection partway between the tabs
//                View nextTitle = getChildAt(selectedPosition + 1);
//                int nextTitleLeft = nextTitle.getLeft();
//                int nextTitleRight = nextTitle.getRight();
//
//                if (!tabIndicatorFullWidth && nextTitle instanceof TabView) {
//                    calculateTabViewContentBounds((TabView) nextTitle, tabViewContentBounds);
//                    nextTitleLeft = (int) tabViewContentBounds.left;
//                    nextTitleRight = (int) tabViewContentBounds.right;
//                }
//
//                left = (int) (selectionOffset * nextTitleLeft + (1.0f - selectionOffset) * left);
//                right = (int) (selectionOffset * nextTitleRight + (1.0f - selectionOffset) * right);
//            }
////
//        } else {
//            left = right = -1;
//        }
//
//        setIndicatorPosition(left, right);
//    }
//
//    private void calculateTabViewContentBounds(LinearLayout tabView, RectF contentBounds) {
//        int tabViewContentWidth = tabView.getContentWidth();
//        int minIndicatorWidth = (int) ViewUtils.dpToPx(getContext(), MIN_INDICATOR_WIDTH);
//
//        if (tabViewContentWidth < minIndicatorWidth) {
//            tabViewContentWidth = minIndicatorWidth;
//        }
//
//        int tabViewCenter = (tabView.getLeft() + tabView.getRight()) / 2;
//        int contentLeftBounds = tabViewCenter - (tabViewContentWidth / 2);
//        int contentRightBounds = tabViewCenter + (tabViewContentWidth / 2);
//
//        contentBounds.set(contentLeftBounds, 0, contentRightBounds, 0);
//    }
//
//
//    void setIndicatorPosition(int left, int right) {
//        if (left != indicatorLeft || right != indicatorRight) {
//            // If the indicator's left/right has changed, invalidate
//            indicatorLeft = left;
//            indicatorRight = right;
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//    }

}

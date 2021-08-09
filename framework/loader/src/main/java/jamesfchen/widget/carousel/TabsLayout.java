package jamesfchen.widget.carousel;

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
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.bumptech.glide.Glide;
import com.jamesfchen.loader.R;

import jamesfchen.widget.DimenUtil;


/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: May/26/2019  Sun
 */
//@CoordinatorLayout.DefaultBehavior(TabsBehavior.class)
public class TabsLayout extends FrameLayout {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private int mOrientation = HORIZONTAL;
    public final int GAP = DimenUtil.dp2px(24, this.getContext());
    private int mCurPosition;
    private ValueAnimator mIndicatorAnimator;

    private LinearLayout mRootView;
    private LinearLayout mLlContainer;
    private FrameLayout mScrollView;
    private View mIndicator;
    private int mTabIndicatorAnimationDuration = 200;

    public TabsLayout(Context context) {
        this(context, null);
    }

    public TabsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public TabsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mRootView = (LinearLayout) View.inflate(context, R.layout.view_tabs_layout, null);
        mLlContainer = mRootView.findViewById(R.id.ll_container);
        mIndicator = mRootView.findViewById(R.id.indicator);
        setOrientation(HORIZONTAL);
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
        removeView(mScrollView);
        if (mScrollView != null) {
            mScrollView.removeView(mRootView);
        }
        ViewGroup.LayoutParams rootViewLp;
        if (mOrientation == HORIZONTAL) {
            mScrollView = new HorizontalScrollView(getContext());
            mScrollView.setHorizontalScrollBarEnabled(false);
            rootViewLp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            mRootView.setOrientation(VERTICAL);
            mLlContainer.setOrientation(HORIZONTAL);
        } else {
            mScrollView = new NestedScrollView(getContext());
            mScrollView.setVerticalScrollBarEnabled(false);
            rootViewLp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            mRootView.setOrientation(HORIZONTAL);
            mLlContainer.setOrientation(VERTICAL);
        }
        mScrollView.addView(mRootView, rootViewLp);
        addView(mScrollView);
    }

    public static class TabItem {
        public String name;
        //        public Drawable drawable;
        int drawableId;
        public String url;

//        public TabItem(String name, Drawable drawable) {
//            this.name = name;
//            this.drawable = drawable;
//        }

        public TabItem(String name, @DrawableRes int resId) {
            this.name = name;
            this.drawableId = resId;
//            this.drawable = ContextCompat.getDrawable( resId);
        }

        public TabItem(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public void setDataList(List<TabItem> tabList) {
        if (tabList == null || tabList.isEmpty()) return;
        mLlContainer.removeAllViews();
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
                            finalI, mTabIndicatorAnimationDuration);
                }
            });
            TabItem tabItem = tabList.get(i);
            TextView tvName = itemView.findViewById(R.id.tv_name);
            tvName.setText(tabItem.name);
            ImageView ivIcon = itemView.findViewById(R.id.iv_icon);
            Drawable d = ContextCompat.getDrawable(getContext(), tabItem.drawableId);
            if (d != null) {
                ivIcon.setImageDrawable(d);
            }

            if (tabItem.url != null && tabItem.url.length() > 0) {
                Glide.with(ivIcon.getContext())
                        .load(tabItem.url)
                        .into(ivIcon);
            }
            MarginLayoutParams layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mOrientation == HORIZONTAL) {
                layoutParams.rightMargin = GAP;
            } else {
                layoutParams.bottomMargin = GAP;
            }
            mLlContainer.addView(itemView, layoutParams);
            if (mLlContainer.getChildCount() > 0) {
                final ViewGroup.LayoutParams indicatorLp = mIndicator.getLayoutParams();
                mLlContainer.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        if (mOrientation == HORIZONTAL) {
                            indicatorLp.width = mLlContainer.getChildAt(0).getWidth();
                            indicatorLp.height = DimenUtil.dp2px(5, getContext());
                        } else {
                            indicatorLp.width = DimenUtil.dp2px(5, getContext());
                            indicatorLp.height = mLlContainer.getChildAt(0).getHeight();
                        }
                        mIndicator.setLayoutParams(indicatorLp);
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

    public void animateIndicatorToPosition(final int position, int duration) {
        if (mIndicatorAnimator != null && mIndicatorAnimator.isRunning()) {
            mIndicatorAnimator.cancel();
            mIndicatorAnimator.removeAllListeners();
        }

        final View curView = mLlContainer.getChildAt(mCurPosition);
        final View targetView = mLlContainer.getChildAt(position);
        Log.d("TabsLayout", "animateIndicatorToPosition:" + curView + "/" + targetView + "--" + position + "/" + mCurPosition);
        if (targetView == null || curView == null || position == mCurPosition) return;

        int curLeft = curView.getLeft();
        int curRight = curView.getRight();
        int targetLeft = targetView.getLeft();
        int targetRight = targetView.getRight();

        int curTop = curView.getTop();
        int curBottom = curView.getBottom();
        int targetTop = targetView.getTop();
        int targetBottom = targetView.getBottom();

        int startValue;
        int endValue;
        String property;
        if (mOrientation == HORIZONTAL) {
            startValue = curLeft;
            endValue = targetLeft;
            property = "translationX";
            int dx = 0;
            if (targetRight > DimenUtil.getScreenWidth(getContext())) {
                dx = targetRight - DimenUtil.getScreenWidth(getContext());
                //todo:fix bug
//            } else if (targetLeft < 0) {
//                dx = targetLeft;
            }
            ((HorizontalScrollView) mScrollView).smoothScrollBy(dx, 0);
        } else {
            startValue = curTop;
            endValue = targetTop;
            property = "translationY";
            int dy = 0;
            if (targetBottom > DimenUtil.getScreenHeight(getContext())) {
                dy = targetBottom - DimenUtil.getScreenWidth(getContext());
                //todo:fix bug
//            } else if (targetLeft < 0) {
//                dx = targetLeft;
            }
            ((NestedScrollView) mScrollView).smoothScrollBy(0, dy);

        }

        mIndicatorAnimator = ObjectAnimator.ofFloat(mIndicator, property, startValue, endValue);
        mIndicatorAnimator.setInterpolator(new FastOutSlowInInterpolator());
        mIndicatorAnimator.setDuration(duration);
        mIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        mIndicatorAnimator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mCurPosition = position;
                    }
                });
        mIndicatorAnimator.start();
    }
}
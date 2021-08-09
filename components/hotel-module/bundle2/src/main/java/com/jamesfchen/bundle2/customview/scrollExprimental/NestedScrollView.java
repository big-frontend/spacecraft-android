package com.jamesfchen.bundle2.customview.scrollExprimental;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class NestedScrollView extends FrameLayout implements NestedScrollingChild3 {
    NestedScrollingChildHelper nestedScrollingChildHelper;
    public NestedScrollView(Context context) {
        super(context);
    }

    public NestedScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setNestedScrollingEnabled(true);//启用嵌套滑动功能
        if (isNestedScrollingEnabled()) {
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        } else {
            stopNestedScroll();
        }

         nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
    }

    /**
     * NestedScrollingChild start:
     * 分发事件
     */
    //NestedScrollingChild
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return nestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d("cjf", "NestedScrollView#dispatchNestedFling");
        return nestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        Log.d("cjf", "NestedScrollView#dispatchNestedPreScroll");
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.d("cjf", "NestedScrollView#dispatchNestedScroll");
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    //NestedScrollingChild2
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        Log.d("cjf", "NestedScrollView#dispatchNestedPreScroll");
        return false;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        Log.d("cjf", "NestedScrollView#dispatchNestedScroll");
        return false;//true if the event was dispatched, false if it could not be dispatched
    }

    //NestedScrollingChild3
    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        Log.d("cjf", "NestedScrollView#dispatchNestedScroll");
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.d("cjf", "NestedScrollView#startNestedScroll");
        return false;
    }
    @Override
    public boolean startNestedScroll(int axes) {
        Log.d("cjf", "NestedScrollView#startNestedScroll");
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        Log.d("cjf", "NestedScrollView#stopNestedScroll");
        nestedScrollingChildHelper.stopNestedScroll(type);

    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        Log.d("cjf", "NestedScrollView#hasNestedScrollingParent");
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }


    @Override
    public boolean hasNestedScrollingParent() {
        Log.d("cjf", "NestedScrollView#hasNestedScrollingParent");
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.d("cjf", "NestedScrollView#setNestedScrollingEnabled");
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.d("cjf", "NestedScrollView#isNestedScrollingEnabled");
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }
    /**
     *
     NestedScrollingChild end
     */
}

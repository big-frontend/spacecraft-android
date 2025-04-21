package com.electrolytej.bundle2.page.customview.scrollExprimental;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;

public class NestedScrollLayout extends FrameLayout implements NestedScrollingParent3 {
    NestedScrollingParentHelper nestedScrollingParentHelper;

    public NestedScrollLayout(Context context) {
        super(context);
    }

    public NestedScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
         nestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    }

    /**
     * NestedScrollingParent start：
     * 处理事件
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d("cjf","NestedScrollLayout#onNestedPreScroll");
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        Log.d("cjf","NestedScrollLayout#onNestedScroll");

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.d("cjf","NestedScrollLayout#onStartNestedScroll");
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
        Log.d("cjf","NestedScrollLayout#onNestedScrollAccepted");
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        nestedScrollingParentHelper.onStopNestedScroll(target, type);
        Log.d("cjf","NestedScrollLayout#onStopNestedScroll");
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d("cjf","NestedScrollLayout#onNestedScroll");

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d("cjf","NestedScrollLayout#onNestedPreScroll");

    }
    /**
     *
     NestedScrollingParent end
     */
}

package com.hawksjamesf.common.listener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/24/2019  Fri
 */
public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes:SCROLL_AXIS_HORIZONTAL=1/SCROLL_AXIS_VERTICAL=2
     * @param type:TYPE_TOUCH=0/TYPE_NON_TOUCH=1
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        Log.d("ViewOffsetBehavior", "onStartNestedScroll\nchild--->" + child + "\ndirectTargetChild--->" + directTargetChild + "\ntarget--->" + target + "\naxes--->" + axes + "\ntype--->" + type);
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d("ViewOffsetBehavior", "onNestedPreScroll");
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
//        Log.d("ViewOffsetBehavior", "onNestedScroll");
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
//        Log.d("ViewOffsetBehavior", "onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        Log.d("ViewOffsetBehavior", "onNestedScrollAccepted");
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

}

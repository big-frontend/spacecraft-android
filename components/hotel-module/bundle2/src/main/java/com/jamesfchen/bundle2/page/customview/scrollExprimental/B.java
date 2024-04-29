package com.jamesfchen.bundle2.page.customview.scrollExprimental;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 *    外部拦截法：父view先拦截，如果不需要就不拦截
 */
public class B extends LinearLayout {
    public B(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScrollView();
    }

    public B(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScrollView();
    }

    private int mTouchSlop;

    private void initScrollView() {
        setFocusable(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private boolean mIsBeingDragged = false;
    private float mLastMotionY;
    private int mActivePointerId;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.d("cjf", "B#onInterceptTouchEvent + " + action);
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        Log.d("cjf", "B#onInterceptTouchEvent + " + action);
//        return super.onInterceptTouchEvent(ev);
        //在捕获了滑动事件，通过onInterceptTouchEvent返回ture拦截不向下传递，通过requestDisallowInterceptTouchEvent不让父节点拦截，这样就能只能自己处理滑动事件，不受其他干扰
        if (action == MotionEvent.ACTION_DOWN) {
            final int y = (int) ev.getY();
            mLastMotionY = y;
            mActivePointerId = ev.getPointerId(0);
//            mVelocityTracker.addMovement(ev);
            mIsBeingDragged = false;
        } else if (action == MotionEvent.ACTION_UP) {
            mIsBeingDragged = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            final int activePointerId = mActivePointerId;
            final int pointerIndex = ev.findPointerIndex(activePointerId);
            final int y = (int) ev.getY(pointerIndex);
            final int yDiff = (int) Math.abs(y - mLastMotionY);
            if (yDiff > mTouchSlop && (getNestedScrollAxes() & SCROLL_AXIS_VERTICAL) == 0) {
                mIsBeingDragged = true;
                mLastMotionY = y;
//                initVelocityTrackerIfNotExists();
//                mVelocityTracker.addMovement(ev);
//                mNestedYOffset = 0;
//                if (mScrollStrictSpan == null) {
//                    mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
//                }
//                final ViewParent parent = getParent();
//                if (parent != null) {
//                    parent.requestDisallowInterceptTouchEvent(true);
//                }
            }

        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.d("cjf", "B#onTouchEvent " + action);
        return super.onTouchEvent(ev);
    }


}

class BB extends LinearLayout {

    public BB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.w("cjf", "BB#onTouchEvent " + action);
        return super.onTouchEvent(ev);
    }

}

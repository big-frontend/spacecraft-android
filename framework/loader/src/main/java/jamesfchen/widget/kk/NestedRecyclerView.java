package jamesfchen.widget.kk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/10/2021  Tue
 */
public class NestedRecyclerView extends RecyclerView {
    private int mTouchSlop;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsUnableToDrag=false;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mActivePointerId;
    public NestedRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getActionMasked() != MotionEvent.ACTION_DOWN) {
//            if (mIsUnableToDrag) {
//                return false;
//            }
//        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        int orientation = 0;
        if (linearLayoutManager != null) {
            orientation = linearLayoutManager.getOrientation();
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int activePointerId = mActivePointerId;
                int pointerIndex = ev.findPointerIndex(activePointerId);
                float x = ev.getX(pointerIndex);
                float y = ev.getY(pointerIndex);
                float dx = mLastMotionX - x;
                float dy = mLastMotionY - y;
                boolean b = canScroll(this, false, (int) dx, (int) x, (int) y, RecyclerView.HORIZONTAL);
                 if (orientation == RecyclerView.HORIZONTAL && Math.abs(dx) > mTouchSlop && canScroll(this, false, (int) dx, (int) x, (int) y, RecyclerView.HORIZONTAL)) {
                    mLastMotionX = x;
                     mIsUnableToDrag = true;
                } else if (orientation == RecyclerView.VERTICAL && Math.abs(dy) > mTouchSlop && canScroll(this, false, (int) dy, (int) x, (int) y, RecyclerView.VERTICAL)) {
                    mLastMotionY = y;
                     mIsUnableToDrag = true;
                } else {
                     mIsUnableToDrag = false;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsUnableToDrag = true;
                break;
        }
        boolean b = super.onInterceptTouchEvent(ev);
        return !mIsUnableToDrag && b;
    }

    /*
     * Negative to check scrolling up, positive to check scrolling down.
     */
    private boolean canScroll(final View v, final boolean checkV, final int delta, final int x, final int y, int orientation) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            int count = group.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = group.getChildAt(i);
                if (child.getLeft() <= x + scrollX && x + scrollX < child.getRight() && child.getTop() <= y + scrollY && y + scrollY < child.getBottom()//判断点击是否位于可滑动的区域
                        && canScroll(child, true, delta, x + scrollX - child.getLeft(), y + scrollY - child.getTop(), orientation)) {
                    return true;
                }
            }
        }
        if (orientation == RecyclerView.HORIZONTAL) {
            return checkV && v.canScrollHorizontally(delta);
        } else {
            return checkV && v.canScrollVertically(delta);
        }
    }


}

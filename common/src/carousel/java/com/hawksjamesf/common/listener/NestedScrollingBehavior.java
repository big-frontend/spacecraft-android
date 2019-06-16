package com.hawksjamesf.common.listener;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.hawksjamesf.common.TabsLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/25/2019  Sat
 */
public class NestedScrollingBehavior extends ViewOffsetBehavior<RecyclerView> {
    private int mTouchSlop;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mActivePointerId;
    public NestedScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull final MotionEvent ev) {
        Log.d("NestedScrollingBehavior", "onInterceptTouchEvent：" + ev.getAction());
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) child.getLayoutManager();
        int orientation = 0;
        if (linearLayoutManager != null) {
            orientation = linearLayoutManager.getOrientation();
        }
        switch (ev.getAction()) {
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
                float dx = x - mLastMotionX;
                float dy = y - mLastMotionY;
                Log.d("NestedScrollingBehavior", "dx/dy>>>>mTouchSlop：" + dx + "/" + dy + ">>>>" + mTouchSlop);
                if (orientation == RecyclerView.HORIZONTAL && Math.abs(dx) > mTouchSlop) {
                    parent.getParent().requestDisallowInterceptTouchEvent(true);//容器类不拦截事件
                } else {
//                    parent.getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (orientation == RecyclerView.HORIZONTAL && Math.abs(dx) > mTouchSlop && canScroll(child, false, (int) dx, (int) x, (int) y, RecyclerView.HORIZONTAL)) {
                    mLastMotionX = x;
                    child.requestDisallowInterceptTouchEvent(true);
                } else if (orientation == RecyclerView.VERTICAL && Math.abs(dy) >mTouchSlop && canScroll(child, false, (int) dy, (int) x, (int) y, RecyclerView.VERTICAL)) {
                    mLastMotionY = y;
                    child.requestDisallowInterceptTouchEvent(true);
                } else {
                    child.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
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
            return checkV && v.canScrollHorizontally(-delta);
        } else {
            return checkV && v.canScrollVertically(-delta);
        }
    }

    RecyclerView.OnScrollListener listener;

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull final View dependency) {
        // We depend on any AppBarLayouts
        if (dependency instanceof TabsLayout) {
            final TabsLayout tabsLayout = (TabsLayout) dependency;
            final LinearLayoutManager layoutManager = (LinearLayoutManager) child.getLayoutManager();
            if (layoutManager == null) return false;
            if (listener == null) {
                listener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            Log.d("NestedScrollingBehavior", "onScrollStateChanged>position:" + layoutManager.findFirstCompletelyVisibleItemPosition());
                            tabsLayout.animateIndicatorToPosition(layoutManager.findFirstCompletelyVisibleItemPosition(), 200);
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                };
            }
            child.removeOnScrollListener(listener);
            child.addOnScrollListener(listener);
            tabsLayout.addOnTabSelectedListener(new TabsLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(View view, int position) {
                    Log.d("NestedScrollingBehavior", "ponTabSelected>position:" + position);
                    layoutManager.scrollToPosition(position);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, int layoutDirection) {
        Log.d("NestedScrollingBehavior", "onLayoutChild:\nchild--->" + child +
                "\nlayoutDirection--->" + layoutDirection);
        final View header = findFirstDependency(parent.getDependencies(child));
        if (header != null) {
            CoordinatorLayout.LayoutParams lp =
                    (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = new Rect();
            available.set(
                    parent.getPaddingLeft() + lp.leftMargin,
                    header.getBottom() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    parent.getHeight() + header.getBottom() - parent.getPaddingBottom() - lp.bottomMargin);
            Rect out = new Rect();
            GravityCompat.apply(
                    resolveGravity(lp.gravity),
                    child.getMeasuredWidth(),
                    child.getMeasuredHeight(),
                    available,
                    out,
                    layoutDirection);
            boolean clipToPadding = child.getClipToPadding();
            if (clipToPadding){

            }else {

            }
            child.layout(out.left, out.top, out.right, out.bottom);
        } else {
            // If we don't have a dependency, let super handle it
            parent.onLayoutChild(child, layoutDirection);
        }

        return true;
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }


    TabsLayout findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof TabsLayout) {
                return (TabsLayout) view;
            }
        }
        return null;
    }

}

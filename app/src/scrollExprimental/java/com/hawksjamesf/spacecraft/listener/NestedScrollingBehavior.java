package com.hawksjamesf.spacecraft.listener;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.hawksjamesf.spacecraft.TabsLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    public NestedScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mActivePointerId;


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.d("NestedScrollingBehavior", "onStartNestedScroll\nchild--->" + child + "\ndirectTargetChild--->" + directTargetChild + "\ntarget--->" + target + "\naxes--->" + axes + "\ntype--->" + type);
        return !canScroll;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d("NestedScrollingBehavior", "onNestedPreScroll");
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    boolean canScroll;

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull MotionEvent ev) {
        Log.d("NestedScrollingBehavior", "onInterceptTouchEvent");
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
                float dx = x - mLastMotionX;
                float y = ev.getY(pointerIndex);
                //Negative to check scrolling up or positive to check scrolling down.
                canScroll = canScroll(child, false, (int) dx, (int) x, (int) y);
                if (dx != 0 && canScroll) {
                    mLastMotionX = x;
                    mLastMotionY = y;
                    ((RecyclerView) child).requestDisallowInterceptTouchEvent(true);
                } else {
                    ((RecyclerView) child).requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    /*
     * Negative to check scrolling left, positive to check scrolling right.
     */
    private boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                // TODO: Add versioned support here for transformed views.
                // This will not work for transformed views in Honeycomb+
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
                        && canScroll(child, true, dx, x + scrollX - child.getLeft(),
                        y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && v.canScrollHorizontally(-dx);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, final View dependency) {
        // We depend on any AppBarLayouts
        if (dependency instanceof TabsLayout) {
            final TabsLayout tabsLayout = (TabsLayout) dependency;
            final LinearLayoutManager layoutManager = (LinearLayoutManager) child.getLayoutManager();

            child.clearOnScrollListeners();
            child.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            });
            tabsLayout.setOnTabSelectedListener(new TabsLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(View view, int position) {
                    Log.d("NestedScrollingBehavior", "ponTabSelected>osition:" + position);
                    layoutManager.scrollToPosition(position);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean onMeasureChild(
            CoordinatorLayout parent,
            RecyclerView child,
            int parentWidthMeasureSpec,
            int widthUsed,
            int parentHeightMeasureSpec,
            int heightUsed) {
        Log.d("TabsBehavior", "onMeasureChild:\nchild--->" + child +
                "\nparentWidthMeasureSpec/widthUsed--->" + parentWidthMeasureSpec + "/" + widthUsed +
                "\nparentHeightMeasureSpec/heightUsed--->" + parentHeightMeasureSpec + "/" + heightUsed);
        final int childLpHeight = child.getLayoutParams().height;
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            // If the menu's height is set to match_parent/wrap_content then measure it
            // with the maximum visible height
            final View header = findFirstDependency(parent.getDependencies(child));
            if (header != null) {
                int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                if (availableHeight > 0 && ViewCompat.getFitsSystemWindows(header)) {
                    WindowInsetsCompat parentInsets = parent.getLastWindowInsets();
                    if (parentInsets != null) {
                        availableHeight += parentInsets.getSystemWindowInsetTop()
                                + parentInsets.getSystemWindowInsetBottom();
                    }
                } else {
                    // If the measure spec doesn't specify a size, use the current height
                    availableHeight = parent.getHeight();
                }

                int headerHeight = header.getMeasuredHeight();
                if (shouldHeaderOverlapScrollingChild()) {
                    child.setTranslationY(-headerHeight);
                } else {
                    availableHeight -= headerHeight;
                }
                final int heightMeasureSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                availableHeight,
                                childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                                        ? View.MeasureSpec.EXACTLY
                                        : View.MeasureSpec.AT_MOST);

                // Now measure the scrolling view with the correct height
                parent.onMeasureChild(
                        child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);

                return true;
            }
        }
        return false;
    }

    protected boolean shouldHeaderOverlapScrollingChild() {
        return false;
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

    private int verticalLayoutGap = 0;
    private int overlayTop;

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

            final WindowInsetsCompat parentInsets = parent.getLastWindowInsets();
            if (parentInsets != null
                    && ViewCompat.getFitsSystemWindows(parent)
                    && !ViewCompat.getFitsSystemWindows(child)) {
                // If we're set to handle insets but this child isn't, then it has been measured as
                // if there are no insets. We need to lay it out to match horizontally.
                // Top and bottom and already handled in the logic above
                available.left += parentInsets.getSystemWindowInsetLeft();
                available.right -= parentInsets.getSystemWindowInsetRight();
            }

            Rect out = new Rect();
            GravityCompat.apply(
                    resolveGravity(lp.gravity),
                    child.getMeasuredWidth(),
                    child.getMeasuredHeight(),
                    available,
                    out,
                    layoutDirection);

            final int overlap = getOverlapPixelsForOffset(header);
            child.layout(out.left, out.top - overlap, out.right, out.bottom - overlap);
            verticalLayoutGap = out.top - header.getBottom();
        } else {
            // If we don't have a dependency, let super handle it
            parent.onLayoutChild(child, layoutDirection);
            verticalLayoutGap = 0;
        }

        return true;
    }

    private static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? GravityCompat.START | Gravity.TOP : gravity;
    }

    final int getOverlapPixelsForOffset(final View header) {
        return overlayTop == 0
                ? 0
                : MathUtils.clamp((int) (getOverlapRatioForOffset(header) * overlayTop), 0, overlayTop);
    }

    float getOverlapRatioForOffset(final View header) {
        return 1f;
    }
}

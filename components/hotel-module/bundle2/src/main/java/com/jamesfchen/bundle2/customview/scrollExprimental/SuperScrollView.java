package com.jamesfchen.bundle2.customview.scrollExprimental;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 * <p>
 * 同方向，需要外部优先滚动，然后在内部滚动
 *
 * NestedScrollView解决了两个同方向的滚动控件，外部先滚动。
 * 解决了ScrollView+RecyclerView滚动时，RecyclerView会优先处理滑动事件，内部先滚动,外部不能处理滚动事件
 * <p>
 * 外部拦截法的典型案例
 */
public class SuperScrollView extends FrameLayout {
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mOverscrollDistance;
    private int mOverflingDistance;
    private Scroller mScroller;
    private OverScroller mOverScroller;

    public SuperScrollView(Context context) {
        this(context, null);
    }

    public SuperScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SuperScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mOverscrollDistance = configuration.getScaledOverscrollDistance();
        mOverflingDistance = configuration.getScaledOverflingDistance();
        mScroller = new Scroller(context);
        mOverScroller = new OverScroller(context);
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker == null) return;
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
//        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, widthPadding + widthUsed, lp.width);

        int heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - heightPadding - heightUsed), MeasureSpec.UNSPECIFIED);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            return;
        }
        Log.d("cjf","onMeasure:heightMode被限制了 "+(heightMode== MeasureSpec.AT_MOST? "at_most":"exactly"));
        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            final int widthPadding;
            final int heightPadding;
            final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
            heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;

            final int desiredHeight = getMeasuredHeight() - heightPadding;
            //给子view高度为scroolview的高度
            if (child.getMeasuredHeight() < desiredHeight) {
                final int childWidthMeasureSpec = getChildMeasureSpec(
                        widthMeasureSpec, widthPadding, lp.width);
                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        desiredHeight, MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }
    private boolean mIsLayoutDirty = true;

    @Override
    public void requestLayout() {
        mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mIsLayoutDirty = false;
    }

    boolean mIsBeingDragged = false;
    int mLastMotionY;
    int mActivePointerId = INVALID_POINTER;
    VelocityTracker mVelocityTracker;
    private static final int INVALID_POINTER = -1;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged) {
            return true;
        }
        if (super.onInterceptTouchEvent(ev)) {
            return true;
        }
        action = action & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            if (!isChild((int) ev.getX(), (int) ev.getY())) {
                mIsBeingDragged = false;
                recycleVelocityTracker();
                return mIsBeingDragged;
            }

            mLastMotionY = (int) ev.getY();
            mActivePointerId = ev.getPointerId(0);
            initOrResetVelocityTracker();
            mVelocityTracker.addMovement(ev);
            mIsBeingDragged = false;
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mIsBeingDragged = false;
            mActivePointerId = INVALID_POINTER;
        } else if (action == MotionEvent.ACTION_MOVE) {
            final int activePointerId = mActivePointerId;
            if (activePointerId == INVALID_POINTER) return mIsBeingDragged;
            final int pointerIndex = ev.findPointerIndex(activePointerId);
            if (pointerIndex == -1) return mIsBeingDragged;
            int x = (int) ev.getX(pointerIndex);
            final int y = (int) ev.getY(pointerIndex);
            final int yDiff = Math.abs(y - mLastMotionY);
            if (yDiff > mTouchSlop) {
                mIsBeingDragged = true;
                mLastMotionY = y;
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                final ViewParent parent = getParent();
                if (parent != null) {
                    //父容器不要拦截move事件
                    parent.requestDisallowInterceptTouchEvent(true);
                }

               if (canScroll(getChildAt(0), false, yDiff, x, y, RecyclerView.VERTICAL)) {
                   mIsBeingDragged = false;
                }else {
                   mIsBeingDragged = true;
               }
            }

        }
        return mIsBeingDragged;
    }
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
    private boolean isChild(int x, int y) {
        View child = getChildAt(0);
        return child.getTop() - getScrollY() <= y && y < child.getBottom() - getScrollY()
                && child.getLeft() <= x && x < child.getRight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = (int) ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) break;
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) break;

                final int y = (int) ev.getY(pointerIndex);
                int yDiff = mLastMotionY - y;
                if (!mIsBeingDragged && Math.abs(yDiff) > mTouchSlop) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        //父容器不要拦截move事件
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = true;
                    if (yDiff > 0) {
                        Log.d("cjf", "向上边滑动👆,yDiff为正数");
                        yDiff -= mTouchSlop;
                    } else {
                        Log.d("cjf", "向下边滑动👇,yDiff为负数");
                        yDiff += mTouchSlop;
                    }
                }
                //开始拖动
                if (mIsBeingDragged) {
//                    Log.d("cjf", "开始拖动。。。。。。");
                    int range = getScrollRange();
                    mLastMotionY = y;
                    if (overScrollBy(0, yDiff, 0, getScrollY(), 0, range, mOverscrollDistance, 0, true)) {
                        mVelocityTracker.clear();
                    }
                    boolean canOverscroll = getOverScrollMode() == OVER_SCROLL_ALWAYS || getOverScrollMode() == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0;
                    if (canOverscroll) {
                        //出边界效果
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsBeingDragged) break;

                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);//每秒1个像素
                int yVelocity = (int) mVelocityTracker.getYVelocity(mActivePointerId);
                if (Math.abs(yVelocity) > mMinimumVelocity) {
                    fling(-yVelocity);
                }

//                else if (mOverScroller.springBack(getScrollX(),getScrollY(),0,getScrollRange(),0,0)){
//                    postInvalidateOnAnimation();
//                }
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mIsBeingDragged) break;


                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    private int getScrollRange() {
        View child = getChildAt(0);
        return Math.max(0, child.getHeight() - (getHeight() - getPaddingLeft() - getPaddingRight()));
    }

    /**
     * overScrollBy方法回调
     * scrollX/scrollY为将要滑动的位置，clampedX到到左侧边界， clampedY到达下边界
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        Log.d("cjf", "onOverScrolled");
        super.scrollTo(scrollX, scrollY);
        if (mOverScroller.isFinished()) {
//            super.scrollTo(scrollX, scrollY);
        } else {
//            int oldx = getScrollX();
//            int oldy = getScrollY();
//            setScrollX(scrollX);
//            setScrollY(scrollY);
//            if (isHardwareAccelerated() && getParent() instanceof View) {
//                ((View) getParent()).invalidate();
//            }
//            onScrollChanged(getScrollX(),getScrollY(),oldx,oldy);
            if (clampedY) {
                mOverScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange());
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void computeScroll() {
//        Log.d("cjf", "computeScroll");
        if (!mOverScroller.computeScrollOffset()) return;
        int oldx = getScrollX();
        int oldy = getScrollY();
        int x = mOverScroller.getCurrX();
        int y = mOverScroller.getCurrY();
        if (x != oldx || y != oldy) {
            int range = getScrollRange();
//            Log.d("cjf", "overScrollBy");
            overScrollBy(x - oldx, y - oldy, oldx, oldy, 0, range, 0, mOverflingDistance, false);
            onScrollChanged(getScrollX(), getScrollY(), oldx, oldy);
            boolean canOverscroll = getOverScrollMode() == OVER_SCROLL_ALWAYS || getOverScrollMode() == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0;
            if (canOverscroll) {
                //出边界效果
            }
        }
        if (!awakenScrollBars()) postInvalidateOnAnimation();
    }

    private void fling(int velocityY) {
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int bottom = getChildAt(0).getHeight();
        mOverScroller.fling(getScrollX(), getScrollY(),
                0, velocityY,//velocities
                0, 0,//x
                0, Math.max(0, bottom - height),//y
                0, bottom / 2);//overscroll
        boolean movingRight = velocityY > 0;
        View currentFocused = findFocus();
        postInvalidateOnAnimation();
    }
}

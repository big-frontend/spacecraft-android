package com.jamesfchen.bundle2.page.customview.scrollExprimental;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 */
public class HScrollView extends FrameLayout {
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mOverscrollDistance;
    private int mOverflingDistance;
    private Scroller mScroller;
    private OverScroller mOverScroller;

    public HScrollView(@NonNull Context context) {
        this(context, null);
    }

    public HScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

//    @Override
//    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
//        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
//
//    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
//        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        //给子类的宽度不限制
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(parentWidthMeasureSpec) - widthPadding - widthUsed), MeasureSpec.UNSPECIFIED);
        int heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;
        int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, heightPadding + heightUsed, lp.height);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        View child = getChildAt(0);
//        LayoutParams lp = (LayoutParams) child.getLayoutParams();
//        int widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
//        int heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;
//        int desireWidth = getMeasuredWidth() - widthPadding;//父容器能提供的预期宽度
//        if (child.getMeasuredWidth() < desireWidth) {
//            MeasureSpec.makeMeasureSpec(desireWidth,MeasureSpec.EXACTLY)
//        }
//    }
    boolean mIsLayoutDirty = true;

    @Override
    public void requestLayout() {
        mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
    }

    //    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        View child = getChildAt(0);
//        LayoutParams lp = (LayoutParams) child.getLayoutParams();
//        int available = right-left-getPaddingLeft()-getPaddingRight() -lp.leftMargin - lp.rightMargin;
//        boolean forceLeftGravity = child.getMeasuredWidth() > available;
//        super.onLayout(forceLeftGravity, left, top, right, bottom);
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("cjf","onDraw");
        super.onDraw(canvas);
    }

    boolean mIsBeingDragged = false;
    int mLastMotionX;
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

            mLastMotionX = (int) ev.getY();
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

            final int x = (int) ev.getX(pointerIndex);
            final int xDiff = Math.abs(x - mLastMotionX);
            if (xDiff > mTouchSlop) {
                mIsBeingDragged = true;
                mLastMotionX = x;
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                final ViewParent parent = getParent();
                if (parent != null) {
                    //父容器不要拦截move事件
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }

        }
        return mIsBeingDragged;
    }

    private boolean isChild(int x, int y) {
        View child = getChildAt(0);
        return child.getTop() <= y && y < child.getBottom()
                && child.getLeft() - getScrollX() <= x && x < child.getRight() - getScrollX();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = (int) ev.getX();
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) break;
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) break;

                final int x = (int) ev.getX(pointerIndex);
                int xDiff = mLastMotionX - x;
                if (!mIsBeingDragged && Math.abs(xDiff) > mTouchSlop) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        //父容器不要拦截move事件
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = true;
                    if (xDiff > 0) {
                        Log.d("cjf", "向左边滑动👈,xDiff为正数");
                        xDiff -= mTouchSlop;
                    } else {
                        Log.d("cjf", "向右边滑动👉,xDiff为负数");
                        xDiff += mTouchSlop;
                    }
                }
                //开始拖动
                if (mIsBeingDragged) {
                    Log.d("cjf", "开始拖动。。。。。。");
                    int range = getScrollRange();
                    mLastMotionX = x;
                    if (overScrollBy(xDiff, 0, getScrollX(), 0, range, 0, mOverscrollDistance, 0, true)) {
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
                int xVelocity = (int) mVelocityTracker.getXVelocity(mActivePointerId);
                if (Math.abs(xVelocity) > mMinimumVelocity) {
                    fling(-xVelocity);
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
        return Math.max(0, child.getWidth() - (getWidth() - getPaddingLeft() - getPaddingRight()));
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
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
            if (clampedX) {
                mOverScroller.springBack(getScrollX(), getScrollY(), 0, getScrollRange(), 0, 0);
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
            overScrollBy(x - oldx, y - oldy, oldx, oldy, range, 0, mOverflingDistance, 0, false);
            onScrollChanged(getScrollX(), getScrollY(), oldx, oldy);
            boolean canOverscroll = getOverScrollMode() == OVER_SCROLL_ALWAYS || getOverScrollMode() == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0;
            if (canOverscroll) {
                //出边界效果
            }
        }
        if (!awakenScrollBars()) postInvalidateOnAnimation();
    }


    private void fling(int velocityX) {
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int right = getChildAt(0).getWidth();
        mOverScroller.fling(getScrollX(), getScrollY(), velocityX, 0, 0, Math.max(0, right - width), 0, 0, width / 2, 0);
        boolean movingRight = velocityX > 0;
        View currentFocused = findFocus();
        postInvalidateOnAnimation();

    }
}

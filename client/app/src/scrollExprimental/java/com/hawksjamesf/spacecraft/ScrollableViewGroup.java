package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/02/2019  Sat
 * <p>
 * <p>
 * scrollTo 采用绝对路径 scrollBy 采用相对路径
 * <p>
 * <p>
 * 1.down事件首先传递到onInterceptTouchEvent方法中
 * <p>
 * 2.onInterceptTouchEvent返回false表示将down事件交由子View来处理；若某一层子View的onTouchEvent返回了true，后续的move、up等事件都将先传递到ViewGroup的onInterceptTouchEvent的方法，并继续层层传递下去，交由子View处理；若子View的onTouchEvent都返回了false，则down事件将交由该ViewGroup的onTouchEvent来处理；如果ViewGroup的onTouchEvent返回false，down传递给父ViewGroup，后续事件不再传递给该ViewGroup；如果ViewGroup的onTouchEvent返回true，后续事件不再经过该ViewGroup的onInterceptTouchEvent方法，直接传递给onTouchEvent方法处理
 * <p>
 * 3.onInterceptTouchEvent返回ture，down事件将转交该ViewGroup的onTouchEvent来处理；若onTouchEvent返回true，后续事件将不再经过该ViewGroup的onInterceptTouchEvent方法，直接交由该ViewGroup的onTouchEvent方法处理；若onTouchEvent方法返回false，后续事件都将交由父ViewGroup处理，不再经过该ViewGroup的onInterceptTouchEvent方法和onTouchEvent方法
 */
public class ScrollableViewGroup extends LinearLayout {
    public static final String TAG = ScrollableViewGroup.class.getSimpleName();

    public ScrollableViewGroup(Context context) {
        super(context);
        init();
    }

    public ScrollableViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        scroller = new Scroller(getContext());
        overScroller = new OverScroller(getContext());
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    VelocityTracker velocityTracker;
    OverScroller overScroller;
    int touchSlop;
    Scroller scroller;
    float startY;
    float endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent--->ACTION:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startY = event.getY();
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float y = event.getY();
                float scrollingY = y - startY;
//                Log.d(TAG, "onTouchEvent--->deltaY:" + scrollingY + "_touchSlop:" + touchSlop);
                if (touchSlop < scrollingY) {//下拉刷新，手势向下，scroll down:正数
//                    scrollingY += endY;
                } else if (touchSlop < -scrollingY) {//上拉加载，手势向上，scroll up:负数
                }

//                scrollingY += endY;
                if (touchSlop < Math.abs(scrollingY)) {
//                    scrollTo(0, -(int) scrollingY);//scrollingY总是以y=0位基线
                    scrollBy(0, -(int) scrollingY);//scrollBy增量移动容易出现跳跃动画
                }

                break;
            }

            case MotionEvent.ACTION_UP: {
                endY = event.getY();
//                scroller.extendDuration(4000);

                float currVelocity = scroller.getCurrVelocity();
                Log.d(TAG, "up:" + currVelocity);
                float yVelocity = -1f;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(event);
                    velocityTracker.computeCurrentVelocity(1000);
                    yVelocity = velocityTracker.getYVelocity();
                }

                scroller.fling(getScrollX(), getScrollY(), 0, (int) (yVelocity == -1f ? currVelocity : yVelocity), 0, 0, 0, 200);
//                scroller.startScroll(getScrollX(), getScrollY(), 0, 200);//弹性滚动
                velocityTracker.recycle();
                velocityTracker = null;
                break;
            }

        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}

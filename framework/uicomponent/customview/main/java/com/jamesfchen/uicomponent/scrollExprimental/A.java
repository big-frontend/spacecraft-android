package com.jamesfchen.uicomponent.scrollExprimental;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.LinearLayout;

/**
 * https://www.jianshu.com/p/982a83271327
 *
 * https://www.midfang.com/android-motionevent-sliding-conflict/
 * 内部拦截法：父view不拦截事件，子view决定消不消费，不消费就给父view
 */
public class A extends LinearLayout {
    public A(Context context) {
        super(context);
    }

    public A(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public A(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.d("cjf", "A#dispatchTouchEvent " + action);
        if (action == MotionEvent.ACTION_DOWN) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.d("cjf", "A#onTouchEvent " + action);
        return super.onTouchEvent(event);

    }
}

class AA extends LinearLayout {
    public AA(Context context) {
        super(context);
    }

    public AA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        Log.w("cjf", "AA#dispatchTouchEvent " + action);
        if (action == MotionEvent.ACTION_DOWN) {
//            mVelocityTracker.addMovement(ev);
            //父节点不要拦截
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == MotionEvent.ACTION_UP) {
        } else if (action == MotionEvent.ACTION_MOVE) {
            final ViewParent parent = getParent();
            if (parent != null) {
                ////父节点要拦截
                parent.requestDisallowInterceptTouchEvent(false);
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.w("cjf", "AA#onTouchEvent " + action);
        if (action == MotionEvent.ACTION_DOWN) {
//            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == MotionEvent.ACTION_UP) {
        } else if (action == MotionEvent.ACTION_MOVE) {
        } else if (action == MotionEvent.ACTION_CANCEL) {
        }
        return super.onTouchEvent(event);
    }
}



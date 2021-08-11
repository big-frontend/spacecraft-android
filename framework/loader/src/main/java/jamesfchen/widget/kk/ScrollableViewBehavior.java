package jamesfchen.widget.kk;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

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
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: May/25/2019  Sat
 */
@Deprecated
class ScrollableViewBehavior extends ViewOffsetBehavior<RecyclerView> {
    public static final String TAG = "ScrollableViewBehavior";

    public ScrollableViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull final View dependency) {
        return dependency instanceof TabsLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, int layoutDirection) {
        Log.d(TAG, "onLayoutChild:\nchild--->" + child + "\nlayoutDirection--->" + layoutDirection);
        final View header = findFirstDependency(parent.getDependencies(child));
        if (header != null) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = new Rect();
            LinearLayoutManager llm = (LinearLayoutManager) child.getLayoutManager();
            if (llm == null) return false;
            int left, right, top, bottom = 0;
            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                left = parent.getPaddingLeft() + lp.leftMargin;
                top = parent.getPaddingTop() + header.getHeight() + lp.topMargin;
                right = parent.getWidth() - parent.getPaddingRight() - lp.rightMargin;
                bottom = parent.getHeight() - parent.getPaddingBottom() - lp.bottomMargin;
            } else {
                left = parent.getPaddingLeft() + header.getMeasuredWidth() + lp.leftMargin;
                top = parent.getPaddingTop() + lp.topMargin;
                right = parent.getWidth() - parent.getPaddingRight() - lp.rightMargin;
                bottom = parent.getHeight() - parent.getPaddingBottom() - lp.bottomMargin;
            }
            available.set(left, top, right, bottom);
            Rect out = new Rect();
            GravityCompat.apply(
                    resolveGravity(lp.gravity),
                    child.getMeasuredWidth(),
                    child.getMeasuredHeight(),
                    available,
                    out,
                    layoutDirection);
            boolean clipToPadding = child.getClipToPadding();
            if (clipToPadding) {

            } else {

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

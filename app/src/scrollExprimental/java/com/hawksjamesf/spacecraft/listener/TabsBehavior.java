package com.hawksjamesf.spacecraft.listener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hawksjamesf.spacecraft.TabsLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/25/2019  Sat
 */
public class TabsBehavior extends ViewOffsetBehavior<TabsLayout> {
    public TabsBehavior() {
        super();
    }

    public TabsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TabsLayout child, @NonNull View dependency) {
//        Log.d("TabsBehavior", "layoutDependsOn:\nparent--->" + parent + "\nchild--->" + child + "\ndependency--->" + dependency);
//        return false;
//    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull TabsLayout tabsLayout,
                                  int parentWidthMeasureSpec, int widthUsed,
                                  int parentHeightMeasureSpec, int heightUsed) {
//        Log.d("TabsBehavior", "onMeasureChild:\ntabsLayout--->" + tabsLayout +
//                "\nparentWidthMeasureSpec/widthUsed--->" + parentWidthMeasureSpec + "/" + widthUsed +
//                "\nparentHeightMeasureSpec/heightUsed--->" + parentHeightMeasureSpec + "/" + heightUsed);
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) tabsLayout.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
            // If the view is set to wrap on it's height, CoordinatorLayout by default will
            // cap the view at the CoL's height. Since the AppBarLayout can scroll, this isn't
            // what we actually want, so we measure it ourselves with an unspecified spec to
            // allow the child to be larger than it's parent
            parent.onMeasureChild(
                    tabsLayout,
                    parentWidthMeasureSpec,
                    widthUsed,
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    heightUsed);
            return true;
        }

        // Let the parent handle it as normal
        return super.onMeasureChild(
                parent, tabsLayout, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }


//
//    @Override
//    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull TabsLayout tabsLayout, int layoutDirection) {
//        Log.d("TabsBehavior", "onLayoutChild:\ntabsLayout--->" + tabsLayout +
//                "\nlayoutDirection--->" + layoutDirection);
//        boolean handled = super.onLayoutChild(parent, tabsLayout, layoutDirection);
//
//        // The priority for actions here is (first which is true wins):
//        // 1. forced pending actions
//        // 2. offsets for restorations
//        // 3. non-forced pending actions
//        final int pendingAction = tabsLayout.getPendingAction();
//        if (offsetToChildIndexOnLayout >= 0 && (pendingAction & PENDING_ACTION_FORCE) == 0) {
//            View child = tabsLayout.getChildAt(offsetToChildIndexOnLayout);
//            int offset = -child.getBottom();
//            if (offsetToChildIndexOnLayoutIsMinHeight) {
//                offset += ViewCompat.getMinimumHeight(child) + tabsLayout.getTopInset();
//            } else {
//                offset += Math.round(child.getHeight() * offsetToChildIndexOnLayoutPerc);
//            }
//            setHeaderTopBottomOffset(parent, tabsLayout, offset);
//        } else if (pendingAction != PENDING_ACTION_NONE) {
//            final boolean animate = (pendingAction & PENDING_ACTION_ANIMATE_ENABLED) != 0;
//            if ((pendingAction & PENDING_ACTION_COLLAPSED) != 0) {
//                final int offset = -tabsLayout.getUpNestedPreScrollRange();
//                if (animate) {
//                    animateOffsetTo(parent, tabsLayout, offset, 0);
//                } else {
//                    setHeaderTopBottomOffset(parent, tabsLayout, offset);
//                }
//            } else if ((pendingAction & PENDING_ACTION_EXPANDED) != 0) {
//                if (animate) {
//                    animateOffsetTo(parent, tabsLayout, 0, 0);
//                } else {
//                    setHeaderTopBottomOffset(parent, tabsLayout, 0);
//                }
//            }
//        }
//
//        // Finally reset any pending states
//        tabsLayout.resetPendingAction();
//        offsetToChildIndexOnLayout = INVALID_POSITION;
//
//        // We may have changed size, so let's constrain the top and bottom offset correctly,
//        // just in case we're out of the bounds
//        setTopAndBottomOffset(
//                MathUtils.clamp(getTopAndBottomOffset(), -tabsLayout.getTotalScrollRange(), 0));
//
//        // Update the AppBarLayout's drawable state for any elevation changes. This is needed so that
//        // the elevation is set in the first layout, so that we don't get a visual jump pre-N (due to
//        // the draw dispatch skip)
//        updateAppBarLayoutDrawableState(
//                parent, tabsLayout, getTopAndBottomOffset(), 0 /* direction */, true /* forceJump */);
//
//        // Make sure we dispatch the offset update
//        tabsLayout.onOffsetChanged(getTopAndBottomOffset());
//
//        return handled;
//    }
}

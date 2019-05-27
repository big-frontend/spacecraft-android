package com.hawksjamesf.common.listener;

import android.content.Context;
import android.util.AttributeSet;

import com.hawksjamesf.common.TabsLayout;

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

//    @Override
//    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull TabsLayout tabsLayout,
//                                  int parentWidthMeasureSpec, int widthUsed,
//                                  int parentHeightMeasureSpec, int heightUsed) {
////        Log.d("TabsBehavior", "onMeasureChild:\ntabsLayout--->" + tabsLayout +
////                "\nparentWidthMeasureSpec/widthUsed--->" + parentWidthMeasureSpec + "/" + widthUsed +
////                "\nparentHeightMeasureSpec/heightUsed--->" + parentHeightMeasureSpec + "/" + heightUsed);
//        final CoordinatorLayout.LayoutParams lp =
//                (CoordinatorLayout.LayoutParams) tabsLayout.getLayoutParams();
//        if (lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
//            // If the view is set to wrap on it's height, CoordinatorLayout by default will
//            // cap the view at the CoL's height. Since the AppBarLayout can scroll, this isn't
//            // what we actually want, so we measure it ourselves with an unspecified spec to
//            // allow the child to be larger than it's parent
//            parent.onMeasureChild(
//                    tabsLayout,
//                    parentWidthMeasureSpec,
//                    widthUsed,
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                    heightUsed);
//            return true;
//        }
//
//        // Let the parent handle it as normal
//        return super.onMeasureChild(
//                parent, tabsLayout, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
//    }

}

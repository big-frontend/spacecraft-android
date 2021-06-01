package com.hawksjamesf.uicomponent.widget.carousel.listener

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: May/24/2019  Fri
 */
class SampleBehavior(
) : CoordinatorLayout.Behavior<View>(
) {

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        Log.d("SampleBehavior", "onMeasureChild")
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        Log.d("SampleBehavior", "onLayoutChild")
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    /**
     * layout behavior start
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        Log.d("SampleBehavior", "layoutDependsOn:parent:$parent \n child:$child \n dependency:$dependency")
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        Log.d("SampleBehavior", "onDependentViewRemoved:parent:$parent \n child:$child \n dependency:$dependency")
        super.onDependentViewRemoved(parent, child, dependency)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        Log.d("SampleBehavior", "onDependentViewChanged:parent:$parent \n child:$child \n dependency:$dependency")
        return super.onDependentViewChanged(parent, child, dependency)
    }
    /**
     * layout behavior end
     */

    /**
     * nested scrolling behavior start
     */
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        Log.d("SampleBehavior", "onStartNestedScroll")
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.d("SampleBehavior", "onNestedPreScroll")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Log.d("SampleBehavior", "onNestedScroll")
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int) {
        Log.d("SampleBehavior", "onNestedScrollAccepted")
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
        Log.d("SampleBehavior", "onStopNestedScroll")
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }


    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.d("SampleBehavior", "onNestedPreFling")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)

    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        Log.d("SampleBehavior", "onNestedFling")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }
    /**
     * nested scrolling behavior end
     */

    /**
     * touch behavior start
     */
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        Log.d("SampleBehavior", "onInterceptTouchEvent")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        Log.d("SampleBehavior", "onTouchEvent")
        return super.onTouchEvent(parent, child, ev)
    }

    override fun blocksInteractionBelow(parent: CoordinatorLayout, child: View): Boolean {
        Log.d("SampleBehavior", "blocksInteractionBelow")
        return super.blocksInteractionBelow(parent, child)
    }
    /**
     * touch behavior end
     */
}
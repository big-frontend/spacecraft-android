package com.hawksjamesf.uicomponent.transitions;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.hawksjamesf.uicomponent.R;


public class ScaleUpTransitionv2 extends Transition {
    private final static String PROPNAME_SCALE_X = "PROPNAME_SCALE_X";
    private final static String PROPNAME_SCALE_Y = "PROPNAME_SCALE_Y";
    public static final String SCALEUP_TRANSITIONNAME = "scale_up";
    public static final String IV_TRANSITIONNAME = "image";
    public static final int MY_ID = R.id.ll_rootview;
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final String PROPNAME_SCREEN_BOUNDS = "android:slide:screenBounds";
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
//        super.captureStartValues(transitionValues);
//        Log.d("hawks", "captureStartValues:" + transitionValues.view);
        if (SCALEUP_TRANSITIONNAME.equals(transitionValues.view.getTransitionName())
//                || IV_TRANSITIONNAME.equals(transitionValues.view.getTransitionName())
        ) {
            int[] startPosition = new int[2];
            transitionValues.view.getLocationOnScreen(startPosition);
            Log.d("hawks", "captureStartValues:startPosition: " + startPosition[0] + " / " + startPosition[1]);
        }
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
//        super.captureEndValues(transitionValues);
//        Log.d("hawks", "captureEndValues:" + transitionValues.view);
        if (SCALEUP_TRANSITIONNAME.equals(transitionValues.view.getTransitionName())
//                || IV_TRANSITIONNAME.equals(transitionValues.view.getTransitionName())
        ) {
            int[] endPosition = new int[2];
            transitionValues.view.getLocationOnScreen(endPosition);
            Log.d("hawks", "captureEndValues:endPosition: " + endPosition[0] + " / " + endPosition[1]);
        }
        captureValues(transitionValues);
    }

    private int[] mTempLoc = new int[2];

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(mTempLoc);
        int left = mTempLoc[0];
        int top = mTempLoc[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, new Rect(left, top, right, bottom));
    }

    @Override
    public boolean isTransitionRequired(@Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return true;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null
                || !SCALEUP_TRANSITIONNAME.equals(endValues.view.getTransitionName())
                || startValues == null
                || !SCALEUP_TRANSITIONNAME.equals(startValues.view.getTransitionName())
        ) {
            return null;
        }
        Log.d("hawks", "createAnimator:" + startValues.view);
        final View animatedView = startValues.view;
        final Rect startBounds = (Rect) startValues.values.get(PROPNAME_SCREEN_BOUNDS);
        final Rect endBounds = (Rect) endValues.values.get(PROPNAME_SCREEN_BOUNDS);

        Log.d("hawks", "createAnimator:startBounds: " + startBounds.left + "," + startBounds.top + "," + startBounds.right + "," + startBounds.bottom);
        Log.d("hawks", "createAnimator:endBounds: " + endBounds.left + "," + endBounds.top + "," + endBounds.right + "," + endBounds.bottom);
        final ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        final Rect rect = new Rect();
        anim.setDuration(2000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float frame = (float) animation.getAnimatedValue();
                int k_top = (endBounds.top - startBounds.top) / endBounds.top;
                int k_bottom = (endBounds.bottom - startBounds.bottom) / endBounds.bottom;
//                int left=startBounds.left+frame*;
                float top = startBounds.top + k_top * frame;
//                int right=endBounds.left-startBounds.right;
                float bottom = startBounds.bottom + k_bottom * frame;
                rect.left = endBounds.left;
                rect.top = Math.round(top);
                rect.right = endBounds.right;
                rect.bottom = Math.round(bottom);
//                animatedView.layout(left,top,right,bottom);
//                animatedView.requestRectangleOnScreen(rect, true);
//                animatedView.requestLayout();
//                animatedView.offsetTopAndBottom();
                Log.d("hawks", "createAnimator:onAnimationUpdate:" + animatedView.getLeft() + "," + animatedView.getTop() + "," + animatedView.getRight() + "," + animatedView.getBottom());
            }

        });
//        return anim;
        return ScaleUpAnimationCreator
                .createAnimation(animatedView, endValues, startBounds.left, startBounds.top,
                        0, 0, startBounds.right, startBounds.bottom, endBounds.right, endBounds.bottom, sDecelerate, this);
    }
}

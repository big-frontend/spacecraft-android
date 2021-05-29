package com.hawksjamesf.uicomponent.animationsExprimental.transitions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

public class ScaleUpAnimationCreator {
    static Animator createAnimation(final View view, TransitionValues values, int viewPosX, int viewPosY,
                                    float startX, float startY, int startWidth, final int startHeight, int endX, final int endY, TimeInterpolator interpolator,
                                    Transition transition) {

//        ViewPropertyAnimator viewPropertyAnimator = view.animate().scaleY(4).;
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 2);
        ValueAnimator scaleY = ObjectAnimator.ofInt(0,1);
        scaleY.setDuration(2000);
        scaleY.setInterpolator(interpolator);
        scaleY.setTarget(view);
        scaleY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = startHeight;
            }
        });
        scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int frame = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = startHeight+frame*(endY-startHeight)/endY;
            }
        });
//        ObjectAnimator pivotY = ObjectAnimator.ofFloat(view, "pivotY", pivotPoint);
//        view.setPivotY(viewPosY);
        return scaleY;

    }
}

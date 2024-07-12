package com.jamesfchen.bundle2.page.customview.animationsExprimental.transitions;


import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class ScaleUpTransition extends Visibility {
    private static final String PROPNAME_SCREEN_BOUNDS = "com.hawksjamesf.uicomponent.transitions:ScaleUpTransition:screenPosition";
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private CalculateSlide mSlideCalculator = sCalculateBottom;
    private float mSlideFraction = 1;
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();

    private interface CalculateSlide {

        /**
         * Returns the translation value for view when it goes out of the scene
         */
        float getGoneX(ViewGroup sceneRoot, View view, float fraction);

        /**
         * Returns the translation value for view when it goes out of the scene
         */
        float getGoneY(ViewGroup sceneRoot, View view, float fraction);
    }

    private static final CalculateSlide sCalculateBottom = new CalculateSlideVertical() {
        @Override
        public float getGoneY(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationY() + sceneRoot.getHeight() * fraction;
        }
    };

    private static abstract class CalculateSlideVertical implements CalculateSlide {

        @Override
        public float getGoneX(ViewGroup sceneRoot, View view, float fraction) {
            return view.getTranslationX();
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        Log.d("hawks", "captureStartValues:" + transitionValues.view);
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view,
                             TransitionValues startValues, TransitionValues endValues) {
        Log.d("hawks", "onAppear:"+view);
        if (endValues == null) {
            return null;
        }
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_POSITION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = mSlideCalculator.getGoneX(sceneRoot, view, mSlideFraction);
        float startY = mSlideCalculator.getGoneY(sceneRoot, view, mSlideFraction);
        return TranslationAnimationCreator
                .createAnimation(view, endValues, position[0], position[1],
                        startX, startY, endX, endY, sDecelerate, this);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view,
                                TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        int[] position = (int[]) startValues.values.get(PROPNAME_SCREEN_POSITION);
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = mSlideCalculator.getGoneX(sceneRoot, view, mSlideFraction);
        float endY = mSlideCalculator.getGoneY(sceneRoot, view, mSlideFraction);
        return TranslationAnimationCreator
                .createAnimation(view, startValues, position[0], position[1],
                        startX, startY, endX, endY, sAccelerate, this);
    }
}

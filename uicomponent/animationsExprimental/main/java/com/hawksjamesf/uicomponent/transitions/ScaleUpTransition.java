package com.hawksjamesf.uicomponent.transitions;


import android.animation.Animator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ScaleUpTransition extends Visibility {
    private static final String PROPNAME_SCREEN_BOUNDS = "com.hawksjamesf.uicomponent.transitions:ScaleUpTransition:screenPosition";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        // Call the convenience method captureValues
        Log.d("hawks","captureStartValues:"+transitionValues.view);
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        Log.d("hawks","captureEndValues");
        captureValues(transitionValues);
    }
    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, position);
//        View view = transitionValues.view;
//        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, view.getBackground());
    }
    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        Log.d("hawks","onAppear1,"+sceneRoot+"  /  "+view);
//        Log.d("hawks","onAppear1,"+sceneRoot+"  /  "+view+" \n "+startValues+" \n "+endValues);
        if (endValues == null) return null;
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_BOUNDS);
        float endX = view.getScaleX();
        float endY = view.getScaleY();
//        float startX =
//        float startY =
        return super.onAppear(sceneRoot, view, startValues, endValues);
    }

//    @Override
//    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
//        Log.d("hawks","onAppear2");
//        return super.onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
//    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        Log.d("hawks","onDisappear1");
        return super.onDisappear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        Log.d("hawks","onDisappear2");
        return super.onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}

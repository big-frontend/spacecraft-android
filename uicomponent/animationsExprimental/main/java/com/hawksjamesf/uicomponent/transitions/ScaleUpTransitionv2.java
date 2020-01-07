package com.hawksjamesf.uicomponent.transitions;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.uicomponent.R;


public class ScaleUpTransitionv2 extends Visibility {
    private final static String PROPNAME_SCALE_X = "PROPNAME_SCALE_X";
    private final static String PROPNAME_SCALE_Y = "PROPNAME_SCALE_Y";
    public static final String SCALEUP_TRANSITIONNAME = "scale_up";
    public static final int MY_ID = R.id.ll_rootview;
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
//        if (!SCALEUP_TRANSITIONNAME.equals(transitionValues.view.getTransitionName())) return;
        Log.d("hawks", "captureValues:" + transitionValues.view);
        if (transitionValues.view.getId() != MY_ID) return;
        int[] position = new int[2];
        transitionValues.view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);

    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        Log.d("hawks", "onAppear:"+view);
        if (endValues == null || startValues == null || view.getId() != MY_ID) return null;
        int[] startPosition = (int[]) startValues.values.get(PROPNAME_SCREEN_POSITION);
        int[] endPosition = (int[]) endValues.values.get(PROPNAME_SCREEN_POSITION);

        Log.d("hawks", "startPosition:startPosition" + startPosition[0] + " / " + startPosition[1]);
        Log.d("hawks", "onAppear:endPosition" + endPosition[0] + " / " + endPosition[1]);
        return super.onAppear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return super.onDisappear(sceneRoot, view, startValues, endValues);
    }
}

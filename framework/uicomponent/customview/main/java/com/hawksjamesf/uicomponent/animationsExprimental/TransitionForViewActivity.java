package com.hawksjamesf.uicomponent.animationsExprimental;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.perf.metrics.AddTrace;

import androidx.annotation.Nullable;
import com.hawksjamesf.uicomponent.R;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class TransitionForViewActivity extends Activity {
    @AddTrace(name = "_transitionForViewActivity_onCreate", enabled = true /* optional */)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_for_view);
    }
}

package com.electrolytej.bundle2.page.customview.animationsExprimental;

import android.app.Activity;
import android.os.Bundle;


import androidx.annotation.Nullable;

import com.electrolytej.bundle2.R;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2018  Sun
 */
public class TransitionForViewActivity extends Activity {
//    @AddTrace(name = "TransitionForViewActivity_onCreate", enabled = true /* optional */)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_for_view);
    }
}

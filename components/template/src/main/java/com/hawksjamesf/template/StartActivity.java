package com.hawksjamesf.template;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hawksjamesf.annotations.TraceTime;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2020  Sun
 */
public class StartActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
    }

    @TraceTime
    @Override
    protected void onStart() {
        super.onStart();
//        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
//        Log.d("cjf", "耗时:" + (System.currentTimeMillis() - start));

    }
}

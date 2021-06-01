package com.jamesfchen.module1;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;

import com.jamesfchen.annotations.TraceTime;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Oct/25/2020  Sun
 */
public class PageActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Module1Activity");
        button.setTextSize(30, TypedValue.COMPLEX_UNIT_SP);
        setContentView(button);
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

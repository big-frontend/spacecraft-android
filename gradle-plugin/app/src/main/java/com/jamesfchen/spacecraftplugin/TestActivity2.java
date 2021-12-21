package com.jamesfchen.spacecraftplugin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jamesfchen.annotations.TraceTime;
import com.jamesfchen.lifecycle.App;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * author jamesfchen
 * email: hawksjamesf@gmail.com
 * since 六月/24/2021  星期四
 */
@App
public class TestActivity2 extends Activity {
    @TraceTime
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long start = System.currentTimeMillis();


        Log.d("cjf","耗时："+(System.currentTimeMillis() - start));
    }
}

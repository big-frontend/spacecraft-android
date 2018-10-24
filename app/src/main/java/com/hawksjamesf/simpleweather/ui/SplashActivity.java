package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.ui.home.HomeActivity;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeActivity.openActivity(this);
        finish();
    }
}

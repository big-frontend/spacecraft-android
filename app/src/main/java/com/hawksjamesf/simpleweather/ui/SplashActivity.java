package com.hawksjamesf.simpleweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hawksjamesf.simpleweather.R;
/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Intent startIntent = new Intent();
        // TODO: 2017/7/4  go to HomeActivity or SetupWizardActivity
        startIntent.setClassName("com.hawksjamesf.simpleweather", "com.hawksjamesf.simpleweather.ui.HomeActivity");
        startActivity(startIntent);
        finish();
    }
}

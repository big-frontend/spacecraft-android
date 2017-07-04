package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.network.NetworkWrapper;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

public class SplashAcvitivy extends AppCompatActivity {
  @Inject
   NetworkWrapper wrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acvitivy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Logger.d(wrapper);
//        getApplicationContext()



//        Intent startIntent=new Intent();
//        // TODO: 2017/7/4  go to HomeActivity or SetupWizardActivity
//        startIntent.setClassName("com.hawksjamesf.simpleweather","com.hawksjamesf.simpleweather.ui.HomeActivity");
//        startActivity(startIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}

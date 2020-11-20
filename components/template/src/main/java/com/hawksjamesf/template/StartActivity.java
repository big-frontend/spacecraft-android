package com.hawksjamesf.template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hawksjamesf.annotations.TraceTime;
import com.hawksjamesf.yposed.YPosedActivity;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2020  Sun
 */
public class StartActivity extends Activity {
    @TraceTime
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button = findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Router.select("module1_page").go(StartActivity.this,new Callback(){
//
//                });
            }
        });


    }

    @TraceTime
    @Override
    protected void onStart() {
        super.onStart();
//        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        Log.d("cjf", "耗时:" + (System.currentTimeMillis() - start));

    }
    @TraceTime
    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        startActivity(new Intent(this, YPosedActivity.class));
    }
}

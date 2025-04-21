package com.electrolytej.bundle2.page.customview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.electrolytej.bundle2.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        View cv = findViewById(R.id.cv);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = cv.getLayoutParams();
                layoutParams.height = 300;
                cv.setLayoutParams(layoutParams);
            }
        });

    }

}

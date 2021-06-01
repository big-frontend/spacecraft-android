package com.jamesfchen.uicomponent;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 三月/15/2021  星期一
 */
public class DispatchEventActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button textView = new Button(this);
//        textView.setTextColor(Color.WHITE);
        textView.setText("my activity 3");
        textView.setBackgroundColor(Color.BLUE);
        setContentView(R.layout.activity_dispatch_event);
    }
}
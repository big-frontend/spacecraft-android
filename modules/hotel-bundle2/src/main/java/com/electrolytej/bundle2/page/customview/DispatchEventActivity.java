package com.electrolytej.bundle2.page.customview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.electrolytej.bundle2.R;

public class DispatchEventActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_event);
    }
}
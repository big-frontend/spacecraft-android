package com.jamesfchen.bundle2.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class OptimizationActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchEditText edit  = new SearchEditText(this);
//        edit.setText("111");
        edit.requestFocus();
        edit.setOnTextChangedListener(text -> Log.d("cjf", "onTextChange:" + text));
        FrameLayout.LayoutParams lp =new FrameLayout.LayoutParams(200,200);
        lp.gravity = Gravity.CENTER;
        findViewById(android.R.id.content).requestFocus();
        setContentView(edit,lp);
    }
}

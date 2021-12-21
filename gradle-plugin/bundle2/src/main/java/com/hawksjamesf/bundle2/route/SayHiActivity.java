package com.hawksjamesf.bundle2.route;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jamesfchen.ibc.router.IBCRouter;


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author: jamesfchen
 * since: Jun/19/2021  Sat
 */
//@Route(path = "/bundle2/sayhi")
public  class SayHiActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button tv = new Button(this);
        tv.setText("say hi");
        tv.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        setContentView(tv,layoutParams);

        tv.setOnClickListener(view -> {
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("jamesfchen://www.jamesfchen.com/hotel/main"));
//                startActivity(intent);
            IBCRouter.goNativeBundle(SayHiActivity.this,"bundle1router/sayme");
        });
    }

}
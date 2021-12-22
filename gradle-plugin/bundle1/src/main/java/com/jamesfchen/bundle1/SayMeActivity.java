package com.jamesfchen.bundle1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author: jamesfchen
 * since: Jun/13/2021  Sun
 */
//@Route(path = "/bundle1/sayme")
public class SayMeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText(getPackageName()+" bundle1 say me ");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        setContentView(tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("jamesfchen://www.jamesfchen.com/hotel/main"));
//                startActivity(intent);
//                IBCRouter.goNativeBundle(SayMeActivity.this,"bundle2router/sayhi");
            }
        });
    }

}

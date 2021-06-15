package com.jamesfchen.myhome.dangrous;

import android.graphics.Color;
import android.os.Build;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 */
class Test2 {
    void test(){
        Integer.valueOf(1000);

        Long d = Long.valueOf(111);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Color.valueOf( Color.parseColor("#1232424"));
        }
    }
}

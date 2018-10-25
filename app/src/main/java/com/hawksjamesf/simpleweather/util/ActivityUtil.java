package com.hawksjamesf.simpleweather.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
public class ActivityUtil {
    public static void openActivity(Activity activity, Class clazz, boolean finish) {
        activity.startActivity(new Intent(activity, clazz));
        if (finish) {
            activity.finish();
        }
    }

}

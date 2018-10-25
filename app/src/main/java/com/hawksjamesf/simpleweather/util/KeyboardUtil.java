package com.hawksjamesf.simpleweather.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hawksjamesf.simpleweather.SimpleWeatherApplication;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
public class KeyboardUtil {
    public static void hideSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) SimpleWeatherApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentView = activity.getCurrentFocus();
        if (currentView == null) currentView = new View(activity);
        manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
    }
}

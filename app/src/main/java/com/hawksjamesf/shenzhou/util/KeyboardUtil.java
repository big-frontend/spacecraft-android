package com.hawksjamesf.shenzhou.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hawksjamesf.shenzhou.App;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Oct/24/2018  Wed
 */
public class KeyboardUtil {
    public static void hideSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentView = activity.getCurrentFocus();
        if (currentView == null) currentView = new View(activity);
        manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
    }
}

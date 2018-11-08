package com.hawksjamesf.spacecraft.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/05/2018  Mon
 */
public class BarUtil {
    public static final int LEAN_BACK = 0;
    public static final int IMMERSIVE = 1;
    public static final int IMMERSIVE_STICKY = 2;

    @IntDef({LEAN_BACK, IMMERSIVE, IMMERSIVE_STICKY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static void setBarsFullscreen(@NonNull final Activity activity, @NonNull @Mode int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag;
            if (mode == LEAN_BACK) {
                flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
            } else {
                flag = mode | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

            }
            setBarFullscreen(activity, flag);
        } else {
            //todo:adapter
        }
    }

    public static void setStatusBarFullscreen(@NonNull final Activity activity, @NonNull @Mode int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag;
            if (mode == LEAN_BACK) {
                flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            } else {
                flag = mode | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

            }
            setBarFullscreen(activity, flag);
        } else {
            //todo:adapter
        }

    }

    public static void setNavBarFullscreen(@NonNull final Activity activity, @NonNull @Mode int mode) {
        int flag;
        if (mode == LEAN_BACK) {
            flag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        } else {
            flag = mode | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setBarFullscreen(activity, flag);
        } else {
            //todo:adapter
        }

    }

    public static void setBarFullscreen(@NonNull final Activity activity, @NonNull final int flag) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        decorView.setSystemUiVisibility(flag);
    }

    public static void setNavBarVisibility(@NonNull final Activity activity, boolean isVisible) {
        if (isVisible) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }

    }


}

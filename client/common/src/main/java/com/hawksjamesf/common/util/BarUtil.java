package com.hawksjamesf.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.ColorInt;
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
    /**
     * 不存在heavily的交互，点击屏幕的任何一处即可让bar显示,会一直显示
     * eg.看视频的场景
     */
    public static final int LEAN_BACK = 0;
    /**
     * 存在重量级的交互，点击屏幕任何一处不能显示bar，只能swap才能显示.会一直显示
     * eg.看书、看图片、幻灯片、游戏等场景
     */
    public static final int IMMERSIVE = 1;
    /**
     * 通过swap显示的bar，几秒之后自动消失,不会有callback，
     * 所以想要实现callback并且自动消失，需要IMMERSIVE结合Handler.postDelayed()实现
     */
    public static final int IMMERSIVE_STICKY = 2;

    @IntDef({LEAN_BACK, IMMERSIVE, IMMERSIVE_STICKY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    /**
     * 对于full screen有三种模式:{@LEAN_BACK}、{@IMMERSIVE}、{@IMMERSIVE_STICKY}
     *
     * @param activity
     * @param mode
     */
    public static void setBarsFullscreen(@NonNull final Activity activity, @NonNull @Mode int mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (mode == LEAN_BACK) {

            } else if (mode == IMMERSIVE) {
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE;//filed requeire API leve 19

            } else if (mode == IMMERSIVE_STICKY) {
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//filed requeire API leve 19

            }
            setBarWithFlag(activity, flag);
        } else {
            //todo:adapter
        }
    }

    /**
     *
     *
     * 配合android:fitsSystemWindows使用
     *
     * @param activity
     */
    public static void setStatusBarTransparent(@NonNull final Activity activity) {
        int targetVersion = Build.VERSION.SDK_INT;
        Window window = activity.getWindow();
        if (Build.VERSION_CODES.KITKAT <= targetVersion && targetVersion <= Build.VERSION_CODES.LOLLIPOP) {
            //Theme:android:windowTranslucentStatus
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (targetVersion >= Build.VERSION_CODES.LOLLIPOP) {
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            setBarWithFlag(activity, flag);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
    }

    public static void setStatusBarBackgroudColor(@NonNull final Activity activity, @ColorInt int colorInt) {

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarImmersive(@NonNull final Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOption = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);

    }

//    public static void setNavBar(@NonNull final Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            int flag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//
//            setBarWithFlag(activity, flag);
//        } else {
//            //todo:adapter
//        }
//
//    }

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

    public static void setBarWithFlag(@NonNull final Activity activity, @NonNull final int flag) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        decorView.setSystemUiVisibility(flag);
    }


}

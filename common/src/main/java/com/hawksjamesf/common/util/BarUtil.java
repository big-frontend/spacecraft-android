package com.hawksjamesf.common.util;

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
 * @RequiresApi - Denotes that the annotated element should only be called on the given API level or higher.
 * @TargetApi - Indicates that Lint should treat this type as targeting a given API level, no matter what the project target is.
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
            setBarsWithFlag(activity, flag);
        }

    }

    public static void setStatusBarImmersive(@NonNull final Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            int flag = 0;
            flag = View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            setBarsWithFlag(activity, flag);
        }

    }

    public static void setNavBarImmersive(@NonNull final Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            int flag = 0;
            flag = View.SYSTEM_UI_FLAG_IMMERSIVE |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

            setBarsWithFlag(activity, flag);
        }
    }

    /**
     * 配合android:fitsSystemWindows使用
     * AppBarLayout、CollapsingToolbarLayout 当4.4+的版本遇上这两个组件时就会使得状态栏的透明度达不到预期效果，
     * 建议在5.0+使用这个两个组件和状态栏透明。
     *
     * @param activity
     */
    public static void setStatusBarTransparent(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setBarsTransparent(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setStatusBarBackgroudColor(@NonNull final Activity activity, @ColorInt int colorInt) {
        setBarsColor(activity, flag_isStatusBar, colorInt, -1);
    }

    public static void setNavBarTransparent(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setBarsTransparent(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }


    public static void setNavBarVisibility(@NonNull final Activity activity, final boolean isVisible) {
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


    public static void setNavBarBackgroundColor(@NonNull Activity activity, @ColorInt final int colorInt) {
        setBarsColor(activity, flag_isNavBar, -1, colorInt);

    }

    /**
     * 设置bar的背景色，5.0+支持
     * <item name="android:statusBarColor">@android:color/transparent</item>
     * <item name="android:navigationBarColor">@android:color/holo_blue_bright</item>
     *
     * @param activity
     * @param flag eg. 0x0011
     * a&~b:   清除标志位b;
     * a|b:       添加标志位b;
     * a&b:     取出标志位b;
     * a^b:    取出a与b的不同部分;
     * @param statusBarColorInt
     * @param NavBarColorInt
     */
    public static final int flag_isStatusBar = 0x00;
    public static final int flag_isNavBar = 0x11;
    //    public static final int both = 0x11 << 1; 左移
    public static final int both = 0x11 >> 1;

    public static void setBarsColor(@NonNull Activity activity,
                                    final int flag,
                                    @ColorInt final int statusBarColorInt,
                                    @ColorInt final int navBarColorInt) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ((flag | flag_isStatusBar) == 0) {
                activity.getWindow().setStatusBarColor(statusBarColorInt);

            }
            if ((flag & flag_isNavBar) != 0) {
                activity.getWindow().setNavigationBarColor(navBarColorInt);
            }
        }
    }

    /**
     * 设置bar为透明，4.4~5.0均为半透明，5.0+为透明
     * <p>
     * 4.4~5.0的配置
     * <style name="AppTheme.BarsTransparent">
     * <!--设置状态栏和导航栏为半透明色-->
     * <item name="android:fitsSystemWindows">true</item>
     * <item name="android:windowTranslucentStatus">true</item>
     * <item name="android:windowTranslucentNavigation">true</item>
     * </style>
     * <p>
     * 5.0+的配置
     * <style name="AppTheme.BarsTransparent">
     * <!--设置状态栏和导航栏为全透明-->
     * <item name="android:fitsSystemWindows">true</item>
     * <item name="android:statusBarColor">@android:color/transparent</item>
     * <item name="android:navigationBarColor">@android:color/holo_blue_bright</item>
     * <item name="android:windowDrawsSystemBarBackgrounds">true</item>
     * </style>
     *
     * @param activity
     * @param windowFlags:4.4~5.0, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
     * @param systemUIFlags:5.0+,  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
     */
    public static void setBarsTransparent(@NonNull final Activity activity, final int windowFlags, final int systemUIFlags) {
        int targetVersion = Build.VERSION.SDK_INT;
        Window window = activity.getWindow();
        if (Build.VERSION_CODES.KITKAT <= targetVersion && targetVersion < Build.VERSION_CODES.LOLLIPOP) {
            //Theme:android:windowTranslucentStatus
            window.addFlags(windowFlags);
        } else if (targetVersion >= Build.VERSION_CODES.LOLLIPOP) {
            int localFlag = View.SYSTEM_UI_LAYOUT_FLAGS | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | systemUIFlags;
            setBarsWithFlag(activity, localFlag);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }

    /**
     * 设置bars的flag
     *
     * @param activity
     * @param flag
     */
    public static void setBarsWithFlag(@NonNull final Activity activity, final int flag) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        decorView.setSystemUiVisibility(flag);
    }


}

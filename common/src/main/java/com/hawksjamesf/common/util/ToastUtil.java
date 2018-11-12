package com.hawksjamesf.common.util;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Oct/31/2018  Wed
 */
public class ToastUtil {
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static Toast sToast;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int yOffset = (int) (64 * Util.getApp().getResources().getDisplayMetrics().density + 0.5);
    private static int bgColor = COLOR_DEFAULT;//setBackgroundColor
    private static int bgResource = -1;//DrawableRes:setBackgroundResource
    private static int msgColor = COLOR_DEFAULT;//ColorInt

    public static void setGravity(@NonNull final int gravity, @NonNull final int xOffset, @NonNull final int yOffset) {
        ToastUtil.gravity = gravity;
        ToastUtil.xOffset = xOffset;
        ToastUtil.yOffset = yOffset;
    }

    //================================
    // @ColorInt:
    // @ColorRes:R.color.black
    //ColorRes to ColorInt:ContextCompat.getColor(getContext(), colorRes)
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtil.bgColor = backgroundColor;
    }

    public static void setBgResource(@NonNull @DrawableRes final int bgResource) {
        ToastUtil.bgResource = bgResource;
    }

    public static void setMsgColor(@NonNull @ColorInt final int msgColor) {
        ToastUtil.msgColor = msgColor;
    }

    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    public static View showCustomShort(@LayoutRes final int layoutId) {
        View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }
    //================================

    public static void show(@StringRes final int resId, @NonNull final int duration, final Object... args) {
        show(String.format(Util.getApp().getResources().getString(resId), args), duration);

    }

    public static void show(@StringRes final int resId, @NonNull final int duration) {
        show(Util.getApp().getResources().getText(resId), duration);
    }

    public static void show(final String format, @NonNull final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }


    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }


    public static void show(@NonNull final CharSequence text, @NonNull final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(Util.getApp(), text, duration);
                TextView tvMessage = sToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(msgColor);
                sToast.setGravity(gravity, xOffset, yOffset);
                setBg(tvMessage);
                sToast.show();
            }
        });
    }

    public static void show(@NonNull final View view, @NonNull final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(Util.getApp());
                sToast.setView(view);
                sToast.setDuration(duration);
                sToast.setGravity(gravity, xOffset, yOffset);
                setBg();
                sToast.show();
            }
        });
    }

    public static void setBg(final TextView tvMessage) {
        View rootView = sToast.getView();
        if (bgResource != -1) {
            rootView.setBackgroundResource(bgResource);
            tvMessage.setBackgroundColor(Color.TRANSPARENT);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable rootBg = rootView.getBackground();
            Drawable msgBg = tvMessage.getBackground();
            if (rootBg != null && msgBg != null) {
                rootBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
                tvMessage.setBackgroundColor(Color.TRANSPARENT);
            } else if (rootBg != null) {
                rootBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));

            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));

            } else {
                rootView.setBackgroundColor(bgColor);

            }
        }
    }

    public static void setBg() {
        View rootView = sToast.getView();
        if (bgResource != -1) {
            rootView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable rootBg = rootView.getBackground();
            if (rootBg != null) {
                rootBg.setColorFilter(bgColor, PorterDuff.Mode.SRC_IN);
            } else {
                ViewCompat.setBackground(rootView, new ColorDrawable(bgColor));
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflater = (LayoutInflater) Util.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater != null ? inflater.inflate(layoutId, null) : null;
    }

}

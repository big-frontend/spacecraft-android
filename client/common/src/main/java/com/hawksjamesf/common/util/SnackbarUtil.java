package com.hawksjamesf.common.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Nov/01/2018  Thu
 */
public class SnackbarUtil {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int COLOR_DEFAULT = 0xFEFFFFFF;
    public static final int COLOR_SUCCESS = 0xFF2BB600;
    public static final int COLOR_WAENING = 0xFFFFC100;
    public static final int COLOR_ERROR = 0xFFFF0000;
    public static final int COLOR_MESSAGE = 0xFFFFFFFF;

    private static WeakReference<Snackbar> sReference;

    private View parent;
    private CharSequence message;
    private int messageColor;
    private int bgColor;
    private int bgResource;
    private int duration;
    private CharSequence actionText;
    private int actionTextColor;
    private View.OnClickListener actionListener;
    private int bottomMargin;

    private SnackbarUtil(View parent) {
        setDefault();
        this.parent = parent;
    }

    private void setDefault() {
        message = "";
        messageColor = COLOR_DEFAULT;
        bgColor = COLOR_DEFAULT;
        bgResource = -1;
        duration = LENGTH_SHORT;
        actionText = "";
        actionTextColor = COLOR_DEFAULT;
        bottomMargin = 0;
    }

    public static SnackbarUtil with(@NonNull final View parent) {
        return new SnackbarUtil(parent);
    }

    public CharSequence getMessage() {
        return message;
    }

    public SnackbarUtil setMessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public int getMessageColor() {
        return messageColor;
    }

    public SnackbarUtil setMessageColor(int messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public int getBgColor() {
        return bgColor;
    }

    public SnackbarUtil setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public int getBgResource() {
        return bgResource;
    }

    public SnackbarUtil setBgResource(int bgResource) {
        this.bgResource = bgResource;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public SnackbarUtil setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public CharSequence getActionText() {
        return actionText;
    }

    public SnackbarUtil setActionText(CharSequence actionText) {
        this.actionText = actionText;
        return this;
    }

    public int getActionTextColor() {
        return actionTextColor;
    }

    public SnackbarUtil setActionTextColor(int actionTextColor) {
        this.actionTextColor = actionTextColor;
        return this;
    }

    public View.OnClickListener getActionListener() {
        return actionListener;
    }

    public SnackbarUtil setActionListener(View.OnClickListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public SnackbarUtil setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
        return this;
    }

    public void show() {
        View view = parent;
        if (view == null) return;
        if (messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(message);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(messageColor);
            spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sReference = new WeakReference<>(Snackbar.make(view, spannableString, duration));
        } else {
            sReference = new WeakReference<>(Snackbar.make(view, message, duration));
        }
        Snackbar snackbar = sReference.get();
        View rootView = snackbar.getView();
        if (bgResource != -1) {
            rootView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            rootView.setBackgroundColor(bgColor);
        }
        if (bottomMargin != 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
            params.bottomMargin = bottomMargin;
        }

        if (actionText.length() > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar.setActionTextColor(actionTextColor);
            }
            snackbar.setAction(actionText, actionListener);
        }

        snackbar.show();
    }

    public void showSuccess() {
        bgColor = COLOR_SUCCESS;
        messageColor = COLOR_MESSAGE;
        actionTextColor = COLOR_MESSAGE;
        show();
    }

    public static void dismiss() {
        if (sReference != null && sReference.get() != null) {
            sReference.get().dismiss();
            sReference = null;
        }
    }

    public static View getView() {
        Snackbar snackbar = sReference.get();
        if (snackbar == null) return null;
        return snackbar.getView();
    }

    public static void addView(@NonNull final int layouId, @NonNull final ViewGroup.LayoutParams params) {
        View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) view;
            View child = LayoutInflater.from(view.getContext()).inflate(layouId, null);
            snackbarLayout.addView(child);
        }
    }
}

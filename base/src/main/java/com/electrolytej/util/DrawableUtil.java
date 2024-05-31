package com.electrolytej.util;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Jun/15/2019  Sat
 */
public class DrawableUtil {
    private static View mView;
    private Drawable backgroundDrawable;
    private int solidColor;

    public static DrawableUtil with(View view) {
        mView = view;
        return new DrawableUtil();
    }

    private DrawableUtil() {
    }

    private void applay() {
        mView.setBackground(backgroundDrawable);
    }
}

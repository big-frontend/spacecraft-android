package com.electrolytej.bundle2.page.customview;

import androidx.annotation.DrawableRes;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 */
public class ViewModel {
    @DrawableRes
    int drawableRes;
    String text;

    public ViewModel(int drawableRes, String text) {
        this.drawableRes = drawableRes;
        this.text = text;
    }
}

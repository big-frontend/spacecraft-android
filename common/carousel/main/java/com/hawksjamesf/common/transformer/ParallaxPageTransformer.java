package com.hawksjamesf.common.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {

        int pageWidth = page.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setScrollX((int) (pageWidth * 0.75 * -1));
        } else if (position <= 1) { // [-1,1]
            if (position < 0) {
                page.setScrollX((int) (pageWidth * 0.75 * position));
            } else {
                page.setScrollX((int) (pageWidth * 0.75 * position));
            }

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setScrollX((int) (pageWidth * 0.75));
        }


    }
}

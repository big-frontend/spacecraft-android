package com.hawksjamesf.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/06/2020  Mon
 */
public class BaseCustView2 extends ViewFlipper {
    private static final String basecustTag="default";
    public BaseCustView2(Context context) {
        super(context);
    }

    public BaseCustView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}

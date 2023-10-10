package com.jamesfchen.vi.util;

import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TimeUnit {
    public static final float MS = 1f;
    public static final float S = 1000f;

    @FloatRange(from = MS, to = S)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}

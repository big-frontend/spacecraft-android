package com.electrolytej.sensor.filter;

public final class A {
    public static float[] smoothFilter(float[] fArr, float[] fArr2) {
        if (fArr2 == null) {
            return fArr;
        }
        int length = fArr.length;
        for (int i16 = 0; i16 < length; i16++) {
            float f16 = fArr[i16];
            fArr2[i16] = (0.92f * f16) + ((f16 - fArr2[i16]) * 0.08f);
        }
        return fArr2;
    }
}

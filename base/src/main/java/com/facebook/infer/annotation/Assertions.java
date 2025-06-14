package com.facebook.infer.annotation;

import javax.annotation.Nullable;

public class Assertions {
    public static <T> T assumeNotNull(@Nullable T object, String explanation) {
        return object;
    }
    /**
     * See {@code #assumeNotNull(T object, String explanation)}.
     *
     * <p>NOTE: prefer always providing an explanation.
     */
    public static <T> T assumeNotNull(@Nullable T object) {
        return object;
    }

}

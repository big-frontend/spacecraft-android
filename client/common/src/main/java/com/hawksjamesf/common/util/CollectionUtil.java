package com.hawksjamesf.common.util;

import java.util.Collection;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2019
 * All right reserved.
 */
public class CollectionUtil {

    public static boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(final Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    @Nullable
    public static <T> T getFirstElement(@Nullable final List<T> list) {
        if (isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Nullable
    public static <T> T getLastElement(@Nullable final List<T> list) {
        if (isNotEmpty(list)) {
            int lastIndex = list.size() - 1;
            if (lastIndex >= 0) {
                return list.get(lastIndex);
            }
        }
        return null;
    }

    public static <T> boolean isValidateElement(@Nullable List<T> list, @IntRange(from = 0) int position) {
        return isNotEmpty(list) && list.get(position) != null;
    }
}

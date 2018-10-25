package com.hawksjamesf.simpleweather.util;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2018  Thu
 */
public class TextUtil {
    public static boolean isEmpty(CharSequence... varag) {
        if (varag != null && varag.length > 0) {

            for (CharSequence s : varag) {
                if (s == null || s.length() == 0) {
                    return true;
                }
            }

            return false;
        }
        return true;
    }
}

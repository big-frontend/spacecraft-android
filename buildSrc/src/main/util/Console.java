package util;

import java.util.Arrays;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/13/2018  Sat
 */
public class Console {

    private static final String TAG_J = "----------------> version plugin for java\n";
    private static final String TAG_K = "----------------> version plugin for kotlin\n";
    private static final String TAG_G = "----------------> version plugin for groovy\n";

    public static void printJ(String... args) {
        print(TAG_J + Arrays.toString(args));
    }

    public static void printK(String... args) {
        print(TAG_K + Arrays.toString(args));
    }


    private static void print(String... args) {
        System.out.println(Arrays.toString(args) + "\n<----------------");

    }
}

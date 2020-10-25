package com.hawksjamesf.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/07/2018  Wed
 */
public class CloseUtil {
    public static void closeIO(final Closeable... closeables){
        if (closeables==null) return;
        for (Closeable c :
                closeables) {
            if (c !=null){
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

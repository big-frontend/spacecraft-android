package com.jamesfchen.av.player;

/**
 * Copyright ® 2019
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/02/2019  Sat
 */

/**
 * Options for scaling the bounds of an image to the bounds of this view.
 */
public enum ScaleType {
    MATRIX(0),
    FIT_XY(1),
    FIT_CENTER(3),
    CENTER(5),
    CENTER_CROP(6),
    //    16_9()
//    4_3
    CENTER_INSIDE(7);

    ScaleType(int ni) {
        nativeInt = ni;
    }

    final int nativeInt;
}

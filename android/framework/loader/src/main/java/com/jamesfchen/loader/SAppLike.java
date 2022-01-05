package com.jamesfchen.loader;

import android.app.Application;
import android.content.Intent;

import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jan/05/2022  Wed
 */
public class SAppLike extends DefaultApplicationLike {
    public SAppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
}
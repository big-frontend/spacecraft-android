package com.jamesfchen.loader;

import android.util.Log;

import com.jamesfchen.lifecycle.AppLifecycle;
import com.jamesfchen.lifecycle.IAppLifecycleObserverAdapter;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Mar/16/2019  Sat
 */
@AppLifecycle
public class AppLifecycleObserver extends IAppLifecycleObserverAdapter {

    @Override
    public void onAppCreate() {
        super.onAppCreate();
        Log.d("AppLifecycleObserver", "onAppCreate");
//        MockManager.init(SApp.getInstance(), BuildConfig.DEBUG);
    }

    @Override
    public void onAppForeground() {
        super.onAppForeground();
        Log.d("AppLifecycleObserver", "onAppForeground");
    }

    @Override
    public void onAppBackground() {
        super.onAppBackground();
        Log.d("AppLifecycleObserver", "onAppBackground");
//        MockManager.clear(SApp.getInstance(), BuildConfig.DEBUG);
    }
}

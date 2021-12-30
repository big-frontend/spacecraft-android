package com.jamesfchen.loader;

import android.util.Log;

import com.jamesfchen.mockserver.BuildConfig;
import com.jamesfchen.mockserver.MockManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Mar/16/2019  Sat
 */
public class AppLifecycleObserver implements LifecycleObserver {
    //can receive zero or one argument
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void connectListener(LifecycleOwner lifecycleOwner) {
        Log.d("AppLifecycleObserver", "connectListener");
        MockManager.init(SApp.getInstance(), BuildConfig.DEBUG);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onBackground(LifecycleOwner lifecycleOwner) {
        Log.d("AppLifecycleObserver", "onBackground");

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void disconnectListener(LifecycleOwner lifecycleOwner) {
        Log.d("AppLifecycleObserver", "disconnectListener");
        MockManager.clear(SApp.getInstance(), BuildConfig.DEBUG);
    }

    //Methods annotated with ON_ANY can receive the second argument
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Log.d("AppLifecycleObserver", "onAny:"+event);


    }
}

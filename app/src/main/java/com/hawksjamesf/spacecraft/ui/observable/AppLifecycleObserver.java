package com.hawksjamesf.spacecraft.ui.observable;

import android.util.Log;

import com.hawksjamesf.mockserver.BuildConfig;
import com.hawksjamesf.mockserver.MockManager;
import com.hawksjamesf.spacecraft.App;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/16/2019  Sat
 */
public class AppLifecycleObserver implements LifecycleObserver {
    //can receive zero or one argument
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void connectListener(LifecycleOwner lifecycleOwner) {
        Log.d("AppLifecycleObserver", "connectListener");
        MockManager.init(App.getInstance(), BuildConfig.DEBUG);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void disconnectListener(LifecycleOwner lifecycleOwner) {
        Log.d("AppLifecycleObserver", "disconnectListener");
        MockManager.clear(App.getInstance(), BuildConfig.DEBUG);
    }

    //Methods annotated with ON_ANY can receive the second argument
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Log.d("AppLifecycleObserver", "onAny:"+event);


    }
}

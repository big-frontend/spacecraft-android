package com.hawksjamesf.spacecraft.ui.observable;

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
class ActivityLifecycleObserver implements LifecycleObserver {
    //can receive zero or one argument
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener(LifecycleOwner lifecycleOwner) {
    }

    //Methods annotated with ON_ANY can receive the second argument
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {

    }
}

package com.jamesfchen.lifecycle;

import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author jamesfchen
 * since Jul/03/2021  Sat
 */
public class Injector {
    /**
     *Class<?> aClass = Class.forName("com.jamesfchen.spacecraftplugin.AppLifecycleObserver");
     */
    public static void injectApp(Class clz){
        try {
            ProcessLifecycleOwner.get().getLifecycle().addObserver((LifecycleObserver) clz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

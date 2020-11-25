package com.hawksjamesf.template;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import dalvik.system.DexClassLoader;
import lab.galaxy.yahfa.HookMain;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/20/2020  Fri
 */
public class App extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initHotFix();
        System.loadLibrary("gadget");
    }
    private void initHotFix() {

        try {
            ClassLoader classLoader = getClassLoader();
            File pluginFile = new File(Environment.getExternalStorageDirectory(), "yposedplugin-debug.apk");
            Log.d("HookInfo", "initHotFix: "+pluginFile.getAbsolutePath());
            DexClassLoader dexClassLoader = new DexClassLoader(pluginFile.getAbsolutePath(), getCodeCacheDir().getAbsolutePath(), null, classLoader);
//            PathClassLoader dexClassLoader = new PathClassLoader(pluginFile.getAbsolutePath(), null, classLoader);
            HookMain.doHookDefault(dexClassLoader, classLoader);
        } catch (Exception e) {
        }

    }
}

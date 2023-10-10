package com.jamesfchen.vi.startup;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_MainActivity_onWindowFocusChanged {
    public static String className = "XActivity";
    public static String methodName = "onWindowFocusChanged";
    public static String methodSig = "(Z)V";
    public static void hook(Activity activity, boolean hasFocus){
        backup(activity,hasFocus);
        TraceUtil.o();
    }

    public static void backup(Activity activity, boolean hasFocus){
        Log.e("HookInfo", "backup not be here");
    }
}

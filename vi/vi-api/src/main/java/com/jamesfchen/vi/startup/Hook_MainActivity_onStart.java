package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_MainActivity_onStart {
    public static String className = "XActivity";
    public static String methodName = "onStart";
    public static String methodSig = "()V";
    public static void hook(Activity activity){
        //debug包性能损耗大：0.589s vs. 0.613s, release包性能损耗小：0.36s vs. 0.393s
        TraceUtil.i(TAG_STARTUP_MONITOR,activity.getClass().getSimpleName()+"#onStart");
        backup(activity);
        TraceUtil.o();
    }

    public static void backup(Activity activity){
        Log.e("HookInfo", "backup not be here");
    }
}

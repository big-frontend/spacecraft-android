package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;
@Keep
public class Hook_MainActivity_onCreate {
    public static String className = "XActivity";
    public static String methodName = "onCreate";
    public static String methodSig = "(Landroid/os/Bundle;)V";
    public static void hook(Activity activity, Bundle savedInstanceState){
        //debug包性能损耗：0.589s vs. 0.613s, release包性能损耗：0.36s vs. 0.393s，都在30ms左右
        TraceUtil.i(TAG_STARTUP_MONITOR,activity.getClass().getSimpleName()+"#onWindowFocusChanged");
        TraceUtil.i(TAG_STARTUP_MONITOR,activity.getClass().getSimpleName()+"#onCreate");
        backup(activity,savedInstanceState);
        TraceUtil.o();
    }

    public static void backup(Activity activity, Bundle savedInstanceState){
        Log.e("HookInfo", "backup not be here");
    }
}

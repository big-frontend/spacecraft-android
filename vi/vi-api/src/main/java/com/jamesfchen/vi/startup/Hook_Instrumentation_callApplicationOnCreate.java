package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.Perf;
import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_Instrumentation_callApplicationOnCreate {
    public static String className = "XInstrumentation";
    public static String methodName = "callApplicationOnCreate";
    public static String methodSig = "(Landroid/app/Application;)V";
    public static void hook(Instrumentation instrumentation,Application application){
        Perf.onCreate(application);
        TraceUtil.i(TAG_STARTUP_MONITOR,application.getClass().getSimpleName()+"#onCreate");
        backup(instrumentation,application);
        TraceUtil.o();
    }

    public static void backup(Instrumentation instrumentation,Application application){
        Log.e("HookInfo", "backup not be here");
    }
}

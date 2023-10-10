package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;

import java.util.List;

@Keep
public class Hook_ActivityThread_installContentProviders {
    public static String className = "android.app.ActivityThread";
    public static String methodName = "installContentProviders";
    public static String methodSig = "(Landroid/content/Context;Ljava/util/List;)V";
    public static void hook(Object activityThread,Context context, List<ProviderInfo> providers){
        TraceUtil.o();//attachBaseContext
        TraceUtil.i(TAG_STARTUP_MONITOR,"ActivityThread#installContentProviders");
        long start = SystemClock.uptimeMillis();
        backup(activityThread,context,providers);
        TraceUtil.o();
        long costSum = 0;
        for (Long cost : Hook_ActivityThread_installProvider.costList){
            costSum += cost;
        }
        Log.e(TAG_STARTUP_MONITOR,"size:"+providers.size()+", installProvider cost:"+costSum/1000f+"s ,publishContentProviders cost:"+(SystemClock.uptimeMillis() - start - costSum)/1000f +"s");
    }

    public static void backup(Object activityThread,Context context, List<ProviderInfo> providers){
        Log.e("HookInfo", "backup not be here");
    }
}

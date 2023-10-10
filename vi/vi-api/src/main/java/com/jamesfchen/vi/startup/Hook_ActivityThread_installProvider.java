package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

@Keep
public class Hook_ActivityThread_installProvider {
    public static String className = "android.app.ActivityThread";
    public static String methodName = "installProvider";
    public static String methodSig = "(Landroid/content/Context;Landroid/app/ContentProviderHolder;Landroid/content/pm/ProviderInfo;ZZZ)Landroid/app/ContentProviderHolder;";
    public static List<Long> costList = new ArrayList<Long>();
    public static Object hook(Object activityThread, Context context,
                              Object holder, ProviderInfo info,
                              boolean noisy, boolean noReleaseNeeded, boolean stable) {
        try {
//            TraceUtil.i(TAG_STARTUP_MONITOR, "ActivityThread#installProvider，" + info.name);
            long start = SystemClock.uptimeMillis();
            Object newholder = backup(activityThread, context, holder, info, noisy, noReleaseNeeded, stable);
//            TraceUtil.o();
            costList.add(SystemClock.uptimeMillis() - start);
            return newholder;
        } catch (Exception e) {
            Log.e(TAG_STARTUP_MONITOR,Log.getStackTraceString(e));

        }
        return backup(activityThread, context, holder, info, noisy, noReleaseNeeded, stable);
    }

    public static Object backup(Object activityThread, Context context,
                                Object holder, ProviderInfo info,
                                boolean noisy, boolean noReleaseNeeded, boolean stable) {
        return holder;
    }
}

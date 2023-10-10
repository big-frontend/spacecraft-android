package com.jamesfchen.vi.render;

import static com.jamesfchen.vi.render.FpsKt.TAG_FRAME_MONITOR;

import android.util.Log;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_ExternalBeginFrameSourceAndroid_doFrame {
    public static String className = "org.chromium.components.viz.service.frame_sinks.ExternalBeginFrameSourceAndroid";
    public static String methodName = "doFrame";
    public static String methodSig = "(J)V";
    public static void hook(Object externalBeginFrameSourceAndroid,long frameTimeNanos){
        TraceUtil.i(TAG_FRAME_MONITOR,"ExternalBeginFrameSourceAndroid#doFrame "+frameTimeNanos);
        backup(externalBeginFrameSourceAndroid,frameTimeNanos);
        TraceUtil.o();
    }

    public static void backup(Object externalBeginFrameSourceAndroid,long frameTimeNanos){
        Log.e("HookInfo", "backup not be here");
    }
}

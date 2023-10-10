package com.jamesfchen.vi.render;

import static com.jamesfchen.vi.render.FpsKt.TAG_FRAME_MONITOR;

import androidx.annotation.Keep;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_CallbackRecord_run {
    public static String className = "android.view.Choreographer$CallbackRecord";
    public static String methodName = "run";
    public static String methodSig = "(J)V";

    public static void hook(Object callbackRecord, long frameTimeNanos) {
        TraceUtil.i(TAG_FRAME_MONITOR, "Choreographer$CallbackRecord#run");
        backup(callbackRecord, frameTimeNanos);
        TraceUtil.o();
    }

    public static void backup(Object callbackRecord, long frameTimeNanos) {
    }
}

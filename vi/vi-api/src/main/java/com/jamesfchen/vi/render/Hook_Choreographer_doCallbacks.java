package com.jamesfchen.vi.render;

import androidx.annotation.Keep;

@Keep
public class Hook_Choreographer_doCallbacks {
    public static String className = "android.view.Choreographer";
    public static String methodName = "doCallbacks";
    public static String methodSig = "(IJ)V";

    public static void hook(Object object, int callbackType, long frameTimeNanos) {
//        if (!Hook_FrameDisplayEventReceiver_run.isDrawing) {
//            backup(object, callbackType, frameTimeNanos);
//            return;
//        }
//        TraceUtil.i(TAG_FRAME_MONITOR, "Choreographer#doCallbacks " + callbackType + " " + frameTimeNanos);
        backup(object, callbackType, frameTimeNanos);
//        TraceUtil.o();

    }

    public static void backup(Object object, int callbackType, long frameIntervalNanos) {
    }

}

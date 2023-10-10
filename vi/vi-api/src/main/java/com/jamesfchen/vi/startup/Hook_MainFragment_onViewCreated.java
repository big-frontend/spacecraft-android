package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_MainFragment_onViewCreated {
    public static String className = "XMainFragment";
    public static String methodName = "onViewCreated";
    public static String methodSig = "(Landroid/view/View;Landroid/os/Bundle;)V";

    public static void hook(Fragment fragment,View view, Bundle savedInstanceState) {
        TraceUtil.i(TAG_STARTUP_MONITOR, "MainFragment#onViewCreated");
        backup(fragment, view, savedInstanceState);
        TraceUtil.o();
    }

    public static void backup(Fragment fragment, View view, Bundle savedInstanceState) {
    }
}

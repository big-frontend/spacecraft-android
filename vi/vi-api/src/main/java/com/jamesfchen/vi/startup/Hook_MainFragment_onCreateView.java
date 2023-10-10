package com.jamesfchen.vi.startup;

import static com.jamesfchen.vi.startup.StartupKt.TAG_STARTUP_MONITOR;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;

import com.jamesfchen.vi.util.TraceUtil;

@Keep
public class Hook_MainFragment_onCreateView {
    public static String className = "XMainFragment";
    public static String methodName = "onCreateView";
    public static String methodSig = "(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;";

    public static View hook(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TraceUtil.i(TAG_STARTUP_MONITOR, "MainFragment#onCreateView");
        View view = backup(fragment, inflater, container, savedInstanceState);
        TraceUtil.o();
        return view;
    }

    public static View backup(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  null;
    }
}

package com.electrolytej.util;

import android.os.Build;
import android.webkit.WebView;

public class UserAgentUtil {
    private static String userAgent;

    public static String getAndroidUserAgent() {
        return Build.VERSION.RELEASE;
    }

    public static String getWebUserAgent() {
        WebView webview =new WebView(Util.getApp());
        return webview.getSettings().getUserAgentString();
    }
}

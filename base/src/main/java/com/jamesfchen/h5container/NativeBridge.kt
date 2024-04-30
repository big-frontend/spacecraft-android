package com.jamesfchen.h5container

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface

class NativeBridge(var context: Context) {

    @JavascriptInterface
    fun nativeSay(): String {
        Log.d("cjf", " native say calling from js")
        return "sb"
    }

    @JavascriptInterface
    fun openDetail() {
        Log.d("cjf", " native say calling from js")
        val i = Intent()
        i.component = ComponentName(context.packageName,"com.electrolytej.feeds.page.detail.DetailActivity")
        context.startActivity(i)
    }
}
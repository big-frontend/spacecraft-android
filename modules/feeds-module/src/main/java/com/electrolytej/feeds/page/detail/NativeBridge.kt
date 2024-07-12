package com.electrolytej.feeds.page.detail

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.annotation.Keep

@Keep
class NativeBridge(var activity: Activity) {
    companion object {
        private const val TAG = "NativeBridge"
    }

    @JavascriptInterface
    fun closePage() {
        Log.d(TAG, "activity close from js")
        activity.finish()
    }

    @JavascriptInterface
    fun readAndWriteStorage(): String {
        Log.d(TAG, " native readAndWriteStorage calling from js")
        return "sb"
    }

    @JavascriptInterface
    fun openDetail() {
        Log.d(TAG, " native openDetail calling from js")
        val i = Intent()
        i.component =
            ComponentName(activity.packageName, "com.electrolytej.feeds.page.detail.DetailActivity")
        activity.startActivity(i)
    }
}
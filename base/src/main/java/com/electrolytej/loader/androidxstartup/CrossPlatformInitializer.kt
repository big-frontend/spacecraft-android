package com.electrolytej.loader.androidxstartup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.metrics.AddTrace
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.QbSdk
import java.nio.charset.Charset

class CrossPlatformInitializer : Initializer<Unit> {
    companion object {
        const val TAG = "CrossPlatformInitializer"
    }

    @AddTrace(name = "CrossPlatformInitializer#create", enabled = true)
    override fun create(context: Context) {
        Log.d(TAG, "CrossPlatformInitializer#create")
        //hybrid flutter react-native init
        QbSdk.initX5Environment(context, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            override fun onViewInitFinished(isX5: Boolean) {}
        })
        val strategy = CrashReport.UserStrategy(context)
        strategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
            override fun onCrashHandleStart(
                crashType: Int,
                errorType: String,
                errorMessage: String,
                errorStack: String
            ): Map<String, String> {
                val map = LinkedHashMap<String, String>();
                val x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(context);
                map["x5crashInfo"] = x5CrashInfo;
                return map;
            }

            override fun onCrashHandleStart2GetExtraDatas(
                crashType: Int,
                errorType: String,
                errorMessage: String,
                errorStack: String
            ): ByteArray? {
                return try {
                    "Extra data.".toByteArray(Charset.forName("UTF-8"));
                } catch (e: Exception) {
                    null;
                }
            }
        })

        CrashReport.initCrashReport(context, "3d62251014", true, strategy);
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
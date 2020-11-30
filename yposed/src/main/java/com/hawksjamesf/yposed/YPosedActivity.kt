package com.hawksjamesf.yposed

import android.app.IActivityManager
import android.content.Context
import android.content.pm.IPackageManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.ServiceManager
import android.util.Log
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexFile
import kotlinx.android.synthetic.main.activity_yposed.*
import java.lang.reflect.Proxy

class YPosedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)
        bt_hook_frida.setOnClickListener {
            bt_hook_frida.text = stringFromJNI()
        }
        var info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        Log.d("cjf", "YPosedActivity firstInstallTime:${info.firstInstallTime} lastUpdateTime:${info.lastUpdateTime}")
        val cert = info.signatures[0].toByteArray()
        Log.i("cjf", "java 第一种获取签名的方法：" + sha1ToHexString(cert))
        var b = ServiceManager.getService("package")
        val bs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info = IPackageManager.Stub.asInterface(b).getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES, 0)
            info.signingInfo.apkContentsSigners[0].toByteArray()
        } else {
            info = IPackageManager.Stub.asInterface(b).getPackageInfo(packageName, PackageManager.GET_SIGNATURES, 0)
            info.signatures[0].toByteArray()
        }
        Log.i("cjf", "java 第二种获取签名的方法：" + sha1ToHexString(bs))
        Log.i("cjf", "jni 第一种获取签名的方法:${getSign(this)}")
        Log.i("cjf", "jni 第二种获取签名的方法:${getSignv2(this)}")
        printAllCalsses(packageName)
    }

    fun printAllCalsses(pkgName: String) {
        Log.d("cjf", "p:$packageCodePath  $pkgName")
        val dexFile = DexFile(packageCodePath)
        val entries = dexFile.entries()
        while (entries.hasMoreElements()) {
            val clzName = entries.nextElement()
            if (clzName.contains("hawksjamesf")) {
                Log.d("cjf", "class name:$clzName")
            }
        }
    }


    fun stringFromJava() = "string  from java"

    @Keep
    external fun stringFromJNI(): String

    @Keep
    external fun getSign(ctx: Context): String

    @Keep
    external fun getSignv2(ctx: Context): String

    companion object {

        init {
            System.loadLibrary("hawks")

        }

    }


}

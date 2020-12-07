package com.hawksjamesf.yposed

import android.app.IActivityManager
import android.content.Context
import android.content.pm.IPackageManager
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.ServiceManager
import android.util.Log
import android.view.IWindowManager
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexFile
import kotlinx.android.synthetic.main.activity_yposed.*
import java.lang.reflect.Proxy
import java.util.*

class YPosedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)
        bt_hook_frida.setOnClickListener {
            bt_hook_frida.text = stringFromJNI()
        }
        var info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        var bs = info.signatures[0].toByteArray()
        Log.d("cjf", "YPosedActivity firstInstallTime:${info.firstInstallTime} lastUpdateTime:${info.lastUpdateTime}")
        Log.i("cjf", "java 第一种获取签名的方法：" + sha1ToHexString(bs))
        val package_b = ServiceManager.getService("package")
        val window_b = ServiceManager.getService("window")

        info = IPackageManager.Stub.asInterface(package_b).getPackageInfo(packageName, PackageManager.GET_SIGNATURES, 0)
        bs = info.signatures[0].toByteArray()
        Log.i("cjf", "java 第二种获取签名的方法：" + sha1ToHexString(bs))
        Log.i("cjf", "jni 第一种获取签名的方法:${getSign(this)}")
        Log.i("cjf", "jni 第二种获取签名的方法:${getSignv2(this)}")
        Log.d("cjf", "IPackageManager isProxyClass:${Proxy.isProxyClass(IPackageManager.Stub.asInterface(package_b)::class.java)} " +
                "IBinder isProxyClass:${Proxy.isProxyClass(package_b.javaClass)} " +
                "IWindowManager isProxyClass：${Proxy.isProxyClass(IWindowManager.Stub.asInterface(window_b).javaClass)}")
//        printAllCalsses(packageName)
//        isMainProcess(this)
        Log.d("cjf", " prop:${getSysProp("ro.product.cpu.abi")}")
//        Timer("cjf").schedule(object : TimerTask() {
//            override fun run() {
//
//            }
//
//        }, 20_000, 24*3600*1000)
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
    external fun main()

    @Keep
    external fun getSign(ctx: Context): String

    @Keep
    external fun getSignv2(ctx: Context): String

    @Keep
    external fun checkSign(ctx: Context): Boolean

    companion object {

        init {
            System.loadLibrary("guard")
        }

    }


}

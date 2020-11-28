package com.hawksjamesf.yposed

import android.content.Context
import android.content.pm.IPackageManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.ServiceManager
import android.util.Log
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yposed.*

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
        info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            IPackageManager.Stub.asInterface(b).getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES, 0)
        } else {
            IPackageManager.Stub.asInterface(b).getPackageInfo(packageName, PackageManager.GET_SIGNATURES, 0)
        }
        Log.i("cjf", "java 第二种获取签名的方法：" + sha1ToHexString(info.signatures[0].toByteArray()))
        Log.i("cjf", "jni 第一种获取签名的方法:${getSign(this)}")
        Log.i("cjf", "jni 第二种获取签名的方法:${getSignv2(this)}")
//        getAllCalssz(classLoader)

    }

    fun getAllCalssz(cl: ClassLoader) {
        try {
            val f = cl::class.java.getDeclaredField("classes")
            f.isAccessible = true
//            val classLoader = Thread.currentThread().contextClassLoader
//            val allCls: List<Class<*>>? = f.get(classLoader) as? List<Class<*>> ?: return
//            for (cls in allCls!!) {
//                val location = cls.getResource('/'.toString() + cls.name.replace('.', '/') + ".class")
//                Log.d("cjf", "<p>$location<p/>")
//            }
        } catch (e: Exception) {
            e.printStackTrace()
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

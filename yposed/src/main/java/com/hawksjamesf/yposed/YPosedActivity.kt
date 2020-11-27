package com.hawksjamesf.yposed

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yposed.*
import java.lang.reflect.Field
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class YPosedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)
        bt_hook_frida.setOnClickListener {
            bt_hook_frida.text = stringFromJNI()
        }
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        info.signatures[0].toByteArray()
        info.signatures[0].toCharsString()
        Log.d("cjf", "YPosedActivity firstInstallTime:${info.firstInstallTime} lastUpdateTime:${info.lastUpdateTime}")
        val cert = info.signatures[0].toByteArray()
        Log.d("cjf", "YPosedActivity sha1:" + sha1ToHexString(cert))
        Log.d("cjf", "apk location:${packageCodePath}")
        getSignature(this)
//        getAllCalssz(classLoader)

    }
    @Keep
    fun sha1ToHexString(cert: ByteArray): String {
        try {
            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for (i in publicKey.indices) {
                val appendString = Integer.toHexString(0xFF and publicKey[i].toInt())
                        .toUpperCase(Locale.US)
                if (appendString.length == 1) hexString.append("0")
                hexString.append(appendString)
                hexString.append(":")
            }
            val result = hexString.toString()
            return result.substring(0, result.length - 1)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
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
    external fun getSignature(ctx: Context): Boolean

    companion object {

        init {
            System.loadLibrary("hawks")

        }

    }


}

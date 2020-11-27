package com.hawksjamesf.yposed

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yposed.*
import java.lang.StringBuilder
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
        Log.d("cjf", "YPosedActivity firstInstallTime:${info.firstInstallTime} lastUpdateTime:${info.lastUpdateTime}")
        val cert = info.signatures[0].toByteArray()
        Log.d("cjf", "YPosedActivity sha1:" + sha1ToHexString(cert))

    }

    fun stringFromJava()="string  from java"
    @Keep
    external fun stringFromJNI(): String

    companion object {

        init {
            System.loadLibrary("hawks")

        }
    }


}

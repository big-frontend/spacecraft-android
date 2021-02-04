package com.hawksjamesf.yposed

import android.app.Activity
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

class YPosedActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)
    }
    fun stringFromJava() = "string  from java"
}

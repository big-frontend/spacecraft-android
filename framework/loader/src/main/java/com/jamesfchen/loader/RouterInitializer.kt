package com.jamesfchen.loader

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter

class RouterInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Log.d("cjf","RouterInitializer#create")
        //        Fresco.initialize(this);
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
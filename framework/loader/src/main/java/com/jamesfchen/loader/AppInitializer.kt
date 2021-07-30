package com.jamesfchen.loader

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Log.d("cjf","AppInitializer#create")
        //        Fresco.initialize(this);
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
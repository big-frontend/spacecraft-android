package com.jamesfchen.main

import android.app.Activity
import android.app.ActivityManager
import android.os.Bundle
import android.util.Log
import com.jamesfchen.loader.SApp

class AccountlApp : SApp() {
    override fun onCreate() {
        super.onCreate()
        //设置为普通统计场景
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
    }
}
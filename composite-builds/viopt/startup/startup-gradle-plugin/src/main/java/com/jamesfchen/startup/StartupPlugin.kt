package com.jamesfchen.startup

import com.android.build.api.transform.TransformInvocation
import com.jamesfchen.ClassInfo
import com.jamesfchen.ScanClassPlugin

class StartupPlugin : ScanClassPlugin() {
    override fun getName() = "startup"

    override fun onScanBegin(transformInvocation: TransformInvocation) {
    }

    override fun onScanClass(info: ClassInfo) {
    }

    override fun onScanEnd() {
    }
}
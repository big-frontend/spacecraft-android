package com.jamesfchen

import com.jamesfchen.kotlin.info
import org.gradle.api.Project
import com.jamesfchen.kotlin.ScanClassPlugin
import com.jamesfchen.kotlin.ClassInfo

class TestKotlinPlugin : ScanClassPlugin() {
    override fun apply(project: Project) {
        super.apply(project)
        info("project[${project}] apply ${this::class.java.simpleName}")
    }

    override fun onScanBegin() {

    }

    override fun onScanClassInDir(info: ClassInfo) {
        info("onScanClassInDir:${info}")

    }

    override fun onScanClassInJar(info: ClassInfo) {
//        info("onScanClassInJar:${info}")
    }


    override fun onScanEnd() {
    }

    override fun pluginName()="TestKotlin"
}
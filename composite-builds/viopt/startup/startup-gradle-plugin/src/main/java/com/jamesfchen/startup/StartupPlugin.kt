package com.jamesfchen.startup

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import com.jamesfchen.ClassInfo
import com.jamesfchen.ScanClassPlugin
import java.io.File

class StartupPlugin : ScanClassPlugin() {
    override fun getName() = "Startup"
    lateinit var dest: File
    override fun onScanBegin(transformInvocation: TransformInvocation) {
        dest = transformInvocation.outputProvider.getContentLocation(
            "Startup", TransformManager.CONTENT_CLASS,
            ImmutableSet.of(QualifiedContent.Scope.PROJECT), Format.DIRECTORY
        )
    }

    override fun onScanClass(info: ClassInfo) {

    }

    override fun onScanEnd() {
    }
}
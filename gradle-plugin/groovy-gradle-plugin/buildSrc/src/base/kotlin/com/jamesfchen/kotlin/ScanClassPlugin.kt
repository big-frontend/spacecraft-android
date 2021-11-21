package com.jamesfchen.kotlin

import androidx.annotation.CallSuper
import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jul/09/2021  Fri
 */
abstract class ScanClassPlugin : Plugin<Project>, IScanClass {
    abstract fun pluginName(): String

    //    protected Set<? super QualifiedContent.Scope> getScopes(){
//        return TransformManager.SCOPE_FULL_PROJECT//app project
//    }
//    Set<QualifiedContent.ContentType> getInputTypes() {
//        return TransformManager.CONTENT_CLASS
//    }
    @CallSuper
    override fun apply(project: Project) {
        if (project.plugins.hasPlugin(AppPlugin::class.java)) {
            val android = project.extensions.getByType(AppExtension::class.java)
            android.registerTransform(ScanClassTransform())
        }
    }

    inner class ScanClassTransform : AbsScanClassTransform() {

        override fun onScanBegin() {
            this@ScanClassPlugin.onScanBegin()
        }

        override fun onScanClassInDir(info: ClassInfo) {
            this@ScanClassPlugin.onScanClassInDir(info)
        }

        override fun onScanClassInJar(info: ClassInfo) {
            this@ScanClassPlugin.onScanClassInJar(info)
        }

        override fun onScanEnd() {
            this@ScanClassPlugin.onScanEnd()
        }

        override fun getName()="${this@ScanClassPlugin.pluginName()}Transform"

        @Override
        override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
            TransformManager.CONTENT_CLASS

        override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
            TransformManager.SCOPE_FULL_PROJECT//app project
    }
}
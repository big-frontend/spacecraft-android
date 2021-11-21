package com.jamesfchen.kotlin

import androidx.annotation.CallSuper
import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.InputStream

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jul/10/2021  Sat
 */
abstract class FastInsertCodePlugin : Plugin<Project>, IInsertCode {
    abstract fun pluginName(): String

    @CallSuper
    override fun apply(project: Project) {
        if (project.plugins.hasPlugin(AppPlugin::class.java)) {
            val android = project.extensions.getByType(AppExtension::class.java)
            android.registerTransform(InsertCodeTransform())
        }
    }

    inner class InsertCodeTransform : AbsInsertCodeTransform() {

        override fun getName() = "${this@FastInsertCodePlugin.pluginName()}Transform"

        override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
            TransformManager.CONTENT_CLASS

        override fun getScopes(): MutableSet<QualifiedContent.ScopeType> =
            TransformManager.SCOPE_FULL_PROJECT//app project

        override fun onInsertCodeBegin() {
            this@FastInsertCodePlugin.onInsertCodeBegin()
        }

        override fun onInsertCode(mather: File, classStream: InputStream, canonicalName: String) =
            this@FastInsertCodePlugin.onInsertCode(mather, classStream, canonicalName)

        override fun onInsertCodeEnd() {
            this@FastInsertCodePlugin.onInsertCodeEnd()
        }
    }
}
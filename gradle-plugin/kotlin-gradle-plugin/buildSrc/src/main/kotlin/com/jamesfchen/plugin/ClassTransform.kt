package com.jamesfchen.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

class ClassTransform(val project: Project): Transform() {
    override fun getName() ="ClassTransform"

    override fun getInputTypes()= TransformManager.CONTENT_CLASS
    override fun getScopes()= TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental()=false
    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
    }
}
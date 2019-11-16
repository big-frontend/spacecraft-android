package com.hawksjamesf.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

class PerformanceTransform extends Transform {
    Project project;

    PerformanceTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "PerformanceTransform"
    }

    /***
     *         TransformManager.CONTENT_DEX
     *         TransformManager.CONTENT_DEX_WITH_RESOURCES
     *         TransformManager.CONTENT_JARS
     *         TransformManager.CONTENT_NATIVE_LIBS
     *         TransformManager.CONTENT_RESOURCES
     *         TransformManager.CONTENT_CLASS
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /***
     * -------android bundle的feature start
     * TransformManager.SCOPE_FEATURES
     * TransformManager.SCOPE_FULL_WITH_FEATURES
     * TransformManager.SCOPE_FULL_WITH_IR_AND_FEATURES
     * -------android bundle的feature end
     *
     * TransformManager.SCOPE_FULL_PROJECT
     * TransformManager.SCOPE_FULL_LIBRARY_WITH_LOCAL_JARS
     * TransformManager.SCOPE_FULL_PROJECT_WITH_LOCAL_JARS
     * TransformManager.SCOPE_FULL_WITH_IR_FOR_DEXING
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        println "PerformancePlugin transform"
        super.transform(transformInvocation)
    }
}
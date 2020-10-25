package com.hawksjamesf.plugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.hawksjamesf.plugin.util.P;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Set;

class TraceTransform extends Transform {
    Project project;

    TraceTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return "TraceTransform";
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
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
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
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        P.debug("PerformancePlugin transform");
        super.transform(transformInvocation);
    }
}
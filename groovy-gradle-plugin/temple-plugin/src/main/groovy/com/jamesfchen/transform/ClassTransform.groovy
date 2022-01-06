package com.jamesfchen.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.internal.pipeline.TransformManager
import com.jamesfchen.ClassInfo
import com.jamesfchen.AbsScanClassTransform
import org.gradle.api.Project

class ClassTransform extends AbsScanClassTransform {
    Project project

    ClassTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return ClassTransform.class.getSimpleName()
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

//    @Override
//    protected byte[] onInsertCode(InputStream is, String canonicalName) {
////        P.info("cjf ${canonicalName}")
//        return null
//    }
    @Override
    void onScanBegin() {

    }

    @Override
    void onScanClassInDir(ClassInfo info) {

    }

    @Override
    void onScanClassInJar(ClassInfo info) {

    }

    @Override
    void onScanEnd() {

    }
}
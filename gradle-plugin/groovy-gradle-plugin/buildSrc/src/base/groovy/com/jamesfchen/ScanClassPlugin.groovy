package com.jamesfchen

import androidx.annotation.CallSuper
import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class ScanClassPlugin implements Plugin<Project>, IScanClass {
    abstract String pluginName()
//    protected Set<? super QualifiedContent.Scope> getScopes(){
//        return TransformManager.SCOPE_FULL_PROJECT//app project
//    }
//    Set<QualifiedContent.ContentType> getInputTypes() {
//        return TransformManager.CONTENT_CLASS
//    }
    @CallSuper
    @Override
    void apply(Project project) {
        def android
        if (project.plugins.hasPlugin(AppPlugin)) {
            android = project.extensions.getByType(AppExtension)
            //groovy中不能使用匿名内部类，否则会报错
            //A problem occurred configuring project ':app'.
            //> java.lang.NullPointerException (no error message)
            android.registerTransform(new ScanClassTransform())
        }
    }
    class ScanClassTransform extends AbsScanClassTransform{

        @Override
        void onScanBegin() {
            ScanClassPlugin.this.onScanBegin()
        }

        @Override
        void onScanClassInDir(ClassInfo classInfo){
            ScanClassPlugin.this.onScanClassInDir(classInfo)
        }
        @Override
        void onScanClassInJar(ClassInfo classInfo){
            ScanClassPlugin.this.onScanClassInJar(classInfo)
        }

        @Override
        void onScanEnd() {
            ScanClassPlugin.this.onScanEnd()
        }

        @Override
        String getName() {
            return "${ScanClassPlugin.this.pluginName()}Transform"
        }

        @Override
        Set<QualifiedContent.ContentType> getInputTypes() {
            return TransformManager.CONTENT_CLASS
        }
        @Override
        Set<? super QualifiedContent.Scope> getScopes() {
//                    ScanClassesPlugin.this.getScopes()
            return TransformManager.SCOPE_FULL_PROJECT//app project
        }
    }

}
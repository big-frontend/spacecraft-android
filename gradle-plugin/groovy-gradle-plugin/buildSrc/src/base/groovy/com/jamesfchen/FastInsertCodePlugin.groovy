package com.jamesfchen

import androidx.annotation.CallSuper
import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.internal.pipeline.TransformManager
abstract class FastInsertCodePlugin implements Plugin<Project>,IInsertCode {
    protected abstract String pluginName()
    @CallSuper
    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
           def android = project.extensions.getByType(AppExtension)
            //groovy中不能使用匿名内部类，否则会报错
            //A problem occurred configuring project ':app'.
            //> java.lang.NullPointerException (no error message)
            android.registerTransform(new InsertCodeTransform())
        }
    }
    class InsertCodeTransform extends AbsInsertCodeTransform {

        @Override
        String getName() {
            return "${FastInsertCodePlugin.this.pluginName()}Transform"
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
        @Override
        void onInsertCodeBegin() {
            FastInsertCodePlugin.this.onInsertCodeBegin()
        }

        @Override
        byte[] onInsertCode(File mather, InputStream classStream, String canonicalName) {
            return FastInsertCodePlugin.this.onInsertCode(mather,classStream,canonicalName)
        }
        @Override
        void onInsertCodeEnd() {
            FastInsertCodePlugin.this.onInsertCodeEnd()
        }
    }
}
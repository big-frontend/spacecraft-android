package com.jamesfchen.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import org.gradle.api.Plugin
import org.gradle.api.Project

class PPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        P.info("project[${project}] apply")
    }
    class MyTransforom extends Transform{

        @java.lang.Override
        java.lang.String getName() {
            return "p_plugin"
        }

        @java.lang.Override
        java.util.Set<QualifiedContent.ContentType> getInputTypes() {
            return null
        }

        @java.lang.Override
        java.util.Set<? super QualifiedContent.Scope> getScopes() {
            return null
        }

        @java.lang.Override
        boolean isIncremental() {
            return false
        }
    }
}

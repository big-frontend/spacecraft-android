package com.hawksjamesf.plugin

import com.android.build.gradle.AppExtension
import com.hawksjamesf.plugin.trace.TraceTransform
import com.hawksjamesf.plugin.util.P
import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        P.info("PerformancePlugin apply start")
        project.extensions.create('PerformancePluginExtension', PerformancePluginExtension)
//        def android = project.extensions.findByType(AppExtension.class)
//        project.android.registerTransform(new TemplateTransform(project))
//        def android = project.extensions.findByType(AppExtension.class)
//        android.registerTransform(new TraceTransform(project))
        project.android.registerTransform(new TraceTransform(project))
        P.info("PerformancePlugin apply end")
    }
}
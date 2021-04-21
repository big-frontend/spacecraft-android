package com.jamesfchen.plugin


import com.jamesfchen.plugin.trace.TraceTransform
import com.jamesfchen.plugin.util.P
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
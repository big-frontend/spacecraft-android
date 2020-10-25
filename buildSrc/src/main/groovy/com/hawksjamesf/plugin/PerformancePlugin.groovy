package com.hawksjamesf.plugin

import com.hawksjamesf.plugin.util.P
import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        P.info("PerformancePlugin apply start")
        project.extensions.create('PerformancePluginExtension',PerformancePluginExtension)
        project.android.registerTransform(new TraceTransform(project))
//        def mt = new MethodTracer()
//        mt.start()
        P.info("PerformancePlugin apply end")
    }
}
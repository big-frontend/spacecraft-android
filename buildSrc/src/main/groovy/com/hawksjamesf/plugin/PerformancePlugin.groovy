package com.hawksjamesf.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        com.hawksjamesf.plugin.util.PrintUtil.printInfo("PerformancePlugin apply start")
//        project.extensions.create()
//        project.android.registerTransform(new PerformanceTransform(project))
        def mt = new MethodTrace()
        mt.start()
        com.hawksjamesf.plugin.util.PrintUtil.printInfo("PerformancePlugin apply end")
    }
}
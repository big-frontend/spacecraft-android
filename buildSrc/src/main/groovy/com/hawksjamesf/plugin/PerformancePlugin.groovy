package com.hawksjamesf.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        PrintUtil.printInfo("PerformancePlugin apply start")
//        project.extensions.create()
//        project.android.registerTransform(new PerformanceTransform(project))

        PrintUtil.printInfo("PerformancePlugin apply end")
    }
}
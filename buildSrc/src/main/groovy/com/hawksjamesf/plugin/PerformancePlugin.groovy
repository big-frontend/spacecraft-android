package com.hawksjamesf.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "PerformancePlugin apply start"
//        project.extensions.create()
//        project.android.registerTransform(new PerformanceTransform(project))

        println "PerformancePlugin apply end"
    }
}
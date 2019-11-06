package com.hawksjamesf.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PerformancePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "apply start"
//        project.extensions.create()

        println "apply end"
    }
}
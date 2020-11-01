package com.hawksjamesf.plugin

import com.hawksjamesf.plugin.util.P
import org.gradle.api.Plugin
import org.gradle.api.Project

class CounterPlugin implements Plugin<Project> {
    Project target
    CounterExtension counterExtension

    @Override
    void apply(Project target) {
        P.info("CounterPlugin apply start")
        this.target = target
        counterExtension = target.extensions.create("counter", CounterExtension)
        if (target.android.hasProperty("applicationVariants")) {
            handleAppVar(subProject)
        } else if (target.android.hasProperty("libraryVariants")) {
            handleLibVar(target)
        } else {
            def srcDirs = target.android.sourceSets.main.java.srcDirs
            P.info("CounterPlugin otherVariants start")
            P.info("CounterPlugin otherVariants end")
        }
//        target.getRootProject().allprojects.each { theproject ->
//            def prop = theproject.getProperties()
////
//
//            PrintUtil.printInfo("CounterPlugin subprojects ${theproject.name}  ${prop.get("android")} ${prop.get("libraryVariants")}  ${prop.get("applicationVariants")}")
//            if (theproject.hasProperty("android")) {
////                createCounterTask(theproject.name, theproject.android.sourceSets.main.java.srcDirs)
//            }
//
//        }
        P.info("CounterPlugin apply end")
    }

    def handleAppVar(Project project) {
        P.info("CounterPlugin applicationVariants start")

        project.beforeEvaluate {
            P.info("CounterPlugin project beforeEvaluate start")
            P.info("CounterPlugin project beforeEvaluate end")

        }
        project.afterEvaluate {
            P.info("CounterPlugin project afterEvaluate start")
//            project.getRootProject().getSubprojects().each{ subProject ->
//                if (subProject.projectDir != null){
//                    println"projectDir: $subProject.projectDir"
//
//                }
//            }
            createCounterTask("",project.android.sourceSets.main.java.srcDirs)
            P.info("CounterPlugin project afterEvaluate end")
        }
        P.info("CounterPlugin applicationVariants end ")
    }

    def handleLibVar(Project project) {
        def srcDirs = target.android.sourceSets.main.java.srcDirs
        P.info("CounterPlugin libraryVariants start")
        project.android.libraryVariants.all { variant ->
            P.info("CounterPlugin libraryVariants ${variant.buildType.name}")
        }
        P.info("CounterPlugin libraryVariants end")

    }

    def createCounterTask(def name, def srcDirs) {
        CounterTask counterTask = target.tasks.create("${name}counter", CounterTask)
        counterTask.doFirst {
            counterTask.srcDirs = srcDirs
            P.info("CounterPlugin task doFirst ${srcDirs.getClass()} ${srcDirs}")
        }
        counterTask.doLast {
        }
    }
}
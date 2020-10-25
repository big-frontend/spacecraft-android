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
        System.setProperty('org.gradle.color.error', 'RED')
        System.setProperty('org.gradle.color.warn', 'YELLOW')
        this.target = target
        counterExtension = target.extensions.create("counter", CounterExtension)
        if (target.android.hasProperty("applicationVariants")) {
            handleAppVar(target)
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
            createCounterTask("",project.android.sourceSets.main.java.srcDirs)
            P.info("CounterPlugin project afterEvaluate end")

        }
        project.android.applicationVariants.all { variant ->
            def buildType = variant.buildType.name
            if (buildType.contains("debug")) {
                return
            }

            P.info("CounterPlugin applicationVariants ${buildType}")
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
            P.info("CounterPlugin task doLast")
        }
    }
}
package com.hawksjamesf.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CounterPlugin implements Plugin<Project> {
    Project target
    CounterExtension counterExtension

    @Override
    void apply(Project target) {
        PrintUtil.printInfo("CounterPlugin apply start")
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
            PrintUtil.printInfo("CounterPlugin otherVariants start")
            PrintUtil.printInfo("CounterPlugin otherVariants end")
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
        PrintUtil.printInfo("CounterPlugin apply end")
    }

    def handleAppVar(Project project) {
        PrintUtil.printInfo("CounterPlugin applicationVariants start")

        project.beforeEvaluate {
            PrintUtil.printInfo("CounterPlugin project beforeEvaluate start")
            PrintUtil.printInfo("CounterPlugin project beforeEvaluate end")

        }
        project.afterEvaluate {
            PrintUtil.printInfo("CounterPlugin project afterEvaluate start")
            createCounterTask("",project.android.sourceSets.main.java.srcDirs)
            PrintUtil.printInfo("CounterPlugin project afterEvaluate end")

        }
        project.android.applicationVariants.all { variant ->
            def buildType = variant.buildType.name
            if (buildType.contains("debug")) {
                return
            }

            PrintUtil.printInfo("CounterPlugin applicationVariants ${buildType}")
        }
        PrintUtil.printInfo("CounterPlugin applicationVariants end ")
    }

    def handleLibVar(Project project) {
        def srcDirs = target.android.sourceSets.main.java.srcDirs
        PrintUtil.printInfo("CounterPlugin libraryVariants start")
        project.android.libraryVariants.all { variant ->
            PrintUtil.printInfo("CounterPlugin libraryVariants ${variant.buildType.name}")
        }
        PrintUtil.printInfo("CounterPlugin libraryVariants end")

    }

    def createCounterTask(def name, def srcDirs) {
        CounterTask counterTask = target.tasks.create("${name}counter", CounterTask)
        counterTask.doFirst {
            counterTask.srcDirs = srcDirs
            PrintUtil.printInfo("CounterPlugin task doFirst ${srcDirs.getClass()} ${srcDirs}")
        }
        counterTask.doLast {
            PrintUtil.printInfo("CounterPlugin task doLast")
        }
    }
}
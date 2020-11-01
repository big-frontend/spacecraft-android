package com.hawksjamesf.plugin.counter

import com.hawksjamesf.plugin.util.P
import org.gradle.api.Plugin
import org.gradle.api.Project

class CounterPlugin implements Plugin<Project> {
    Project project
    CounterExtension counterExtension

    @Override
    void apply(Project project) {
        P.info("CounterPlugin apply start")
        this.project = project
        counterExtension = project.extensions.create("counterConfig", CounterExtension)
        def total_srcDir = new ArrayList<File>()

        project.getRootProject().getSubprojects().each { subProject ->
            subProject.afterEvaluate {
                if (counterExtension.seaSrcDirs) {
                    if (subProject.getSubprojects().size() == 0 && subProject.hasProperty("android")) {
                        println("CounterPlugin subproject ${subProject.android.sourceSets.main.java.srcDirs}")
                        total_srcDir.addAll(subProject.android.sourceSets.main.java.srcDirs)
                    }
                }else {
                    if (subProject.name == project.name) {
                        println("CounterPlugin subproject ${subProject.android.sourceSets.main.java.srcDirs}")
                        total_srcDir.addAll(subProject.android.sourceSets.main.java.srcDirs)
                    }
                }
            }
        }

        if (project.android.hasProperty("applicationVariants")) {
            P.info("CounterPlugin applicationVariants start")

            project.beforeEvaluate {
                P.info("CounterPlugin project beforeEvaluate start")
                P.info("CounterPlugin project beforeEvaluate end")
            }
            project.afterEvaluate {
                P.info("CounterPlugin project afterEvaluate start")
                createCounterTask(total_srcDir)
                P.info("CounterPlugin project afterEvaluate end")
            }
            P.info("CounterPlugin applicationVariants end ")
        } else if (project.android.hasProperty("libraryVariants")) {
            project.afterEvaluate {
                createCounterTask(total_srcDir)
            }
        }
        P.info("CounterPlugin apply end")
    }

    def createCounterTask(List<File> srcDirs) {
        if (srcDirs.size() ==0) return
        CounterTask counterTask = project.tasks.create("counter", CounterTask)
        counterTask.doFirst {
            counterTask.srcDirs = srcDirs
        }
        counterTask.doLast {
        }
    }
}
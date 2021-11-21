package com.jamesfchen.counter

import org.gradle.api.Plugin
import org.gradle.api.Project

class CounterPlugin implements Plugin<Project> {
    CounterExtension counterExtension

    @Override
    void apply(Project project) {
        counterExtension = project.extensions.create("counterConfig", CounterExtension)
        def total_srcDir = new ArrayList<File>()

        project.gradle.projectsEvaluated {
            project.rootProject.allprojects.each { subProject ->
                if (counterExtension.seaSrcDirs) {
                    if (subProject.getSubprojects().size() == 0 && subProject.hasProperty("android")) {
//                            println("CounterPlugin subproject java:${subProject.android.sourceSets.main.java.srcDirs} jni:${subProject.android.sourceSets.main.jni.srcDirs}")

                            total_srcDir.addAll(subProject.android.sourceSets.main.java.srcDirs)
                            total_srcDir.addAll(subProject.android.sourceSets.main.jni.srcDirs)
                    }
                }else {
                    if (subProject.name == project.name) {
//                            println("CounterPlugin subproject java:${subProject.android.sourceSets.main.java.srcDirs} jni:${subProject.android.sourceSets.main.jni.srcDirs}")
                            total_srcDir.addAll(subProject.android.sourceSets.main.java.srcDirs)
                            total_srcDir.addAll(subProject.android.sourceSets.main.jni.srcDirs)
                    }
                }
            }
        }

        if (project.android.hasProperty("applicationVariants")) {
            project.afterEvaluate {
                createCounterTask(project,total_srcDir)
            }
        } else if (project.android.hasProperty("libraryVariants")) {
            project.afterEvaluate {
                createCounterTask(project,total_srcDir)
            }
        }
    }

    def createCounterTask(Project project,List<File> srcDirs) {
        if (srcDirs.size() ==0) return
        CounterTask counterTask = project.tasks.create("counter", CounterTask)
        counterTask.doFirst {
            counterTask.srcDirs = srcDirs
        }
        counterTask.doLast {
        }
    }
}
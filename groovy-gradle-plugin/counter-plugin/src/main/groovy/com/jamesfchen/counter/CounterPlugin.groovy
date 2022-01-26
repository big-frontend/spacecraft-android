package com.jamesfchen.counter

import org.gradle.api.Plugin
import org.gradle.api.Project

class CounterPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("counterConfig", CounterExtension)
        def totalSrcDir = new ArrayList<File>();
        project.tasks.create(name: "fileLinesCounter", group: 'counter', type: FileLinesCounter, constructorArgs: [totalSrcDir]).doFirst {
            def counterExtension = project["counterConfig"] as CounterExtension
            project.rootProject.allprojects.each { proj ->
                if (!proj.subprojects.isEmpty()) return
                if (counterExtension.pickupModules != null && counterExtension.pickupModules.contains(proj.name)) {
                    totalSrcDir.addAll(proj.android.sourceSets.main.java.srcDirs)
                    totalSrcDir.addAll(proj.android.sourceSets.main.jni.srcDirs)
                } else if (counterExtension.sea == 'all' && proj.hasProperty("android")) {
                    totalSrcDir.addAll(proj.android.sourceSets.main.java.srcDirs)
                    totalSrcDir.addAll(proj.android.sourceSets.main.jni.srcDirs)
                }
            }
        }
        project.tasks.create(name:'appSizeCounter',group: 'counter',type:AppSizeCounter){
            doFirst{

            }
            doLast {

            }
        }
        project.tasks.create(name:'libSizeCounter',group: 'counter',type:LibSizeCounter){
            doFirst{

            }
            doLast {

            }
        }
    }
}
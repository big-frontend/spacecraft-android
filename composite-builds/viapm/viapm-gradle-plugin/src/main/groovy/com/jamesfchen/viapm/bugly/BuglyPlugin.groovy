package com.jamesfchen.viapm.bugly

import com.jamesfchen.P
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuglyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        P.info("project[${project}] apply ${this.getClass().getSimpleName()}")
        project.extensions.create("bugly", BuglyExtension)
//        if (!project.plugins.hasPlugin("com.android.application")) throw new IllegalArgumentException("bugly plugin必须在app模块中配置")

        //project 加载完成之后
        project.gradle.projectsLoaded {

        }
        //project evaluate完成之后
        project.gradle.projectsEvaluated {
            //todo:有问题 找不到externalNativeBuildDebug任务
//            project.android.applicationVariants.all { variant ->
//                String variantName = variant.name.capitalize()
//                ReportMappingTask mappingTask = project.tasks.create("reportMapping${variantName}", ReportMappingTask)
//                mappingTask.doFirst {
//                    mappingTask.variant = variant
//                }
//                mappingTask.group = Constants.TASK_GROUP
//                if (containTask(project, "package${variantName}")) {
//                    mappingTask.dependsOn project.tasks["package${variantName}"]
//                }
//
//                ReportSoTask soTask = project.tasks.create("reportSo${variantName}", ReportSoTask)
//                soTask.doFirst {
//                    soTask.variant = variant
//                }
//                soTask.group = Constants.TASK_GROUP
//                if (containTask(project.rootProject, "package${variantName}JniLibs")) {
//                    soTask.dependsOn project.tasks["package${variantName}JniLibs"]
//                } else if (containTask(project.rootProject,"externalNativeBuild${variantName}")) {
//                    soTask.dependsOn project.tasks["externalNativeBuild${variantName}"]
//                }
//            }
        }
    }

    boolean containTask(Project project, String taskName) {
        for (def task in project.tasks) {
            if (task.name == taskName) {
                println("cjf ${taskName}")
                return true
            }
        }
        return false
    }
}
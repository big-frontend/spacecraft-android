package com.jamesfchen.plugin

import com.android.build.gradle.internal.plugins.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.hasPlugin
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import  com.android.build.gradle.api.BaseVariant
import  com.android.build.gradle.api.ApplicationVariant

class PerfPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.pluginManager.apply()
//        project.plugins.apply()
        info("project[${project}] apply")
        project.extensions.create<PerfExtension>("perf")
        project.plugins.hasPlugin(AppPlugin::class)

        var android: BaseExtension
//        var variants:BaseVariant
        if (project.plugins.hasPlugin(AppPlugin::class)) {
            android = project.extensions.getByType<AppExtension>()
            val variants = android.applicationVariants
        } else {
            android = project.extensions.getByType<LibraryExtension>()
            val variants = android.libraryVariants
        }
//        android.registerTransform(ClassTransform(project))
    }
}
//fun afterEvaluate(c: Consumer<Project>): Action<in Project> {
//    return Action { project: Project ->
//        try {
////            c.accept(project)
//        } catch (t: Throwable) {
//            throw t
//        }
//    }
//}
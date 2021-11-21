package com.jamesfchen

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def android
        def variants
        if (project.plugins.hasPlugin(AppPlugin)) {
            android = project.extensions.getByType(AppExtension)
            variants = project.android.applicationVariants
        } else {
            android = project.extensions.getByType(LibraryExtension)
            variants = project.android.libraryVariants
        }
//        android.registerTransform(new JarsTransform(project))
//        android.registerTransform(new ResTransform(project))
//        android.registerTransform(new ClassTransform(project))
//        android.registerTransform(new ClassTransform2(project))
        //        project.android.registerTransform(new TemplateTransform(project))
//        def kapt1Configuration = project.configurations.create("kapt1").extendsFrom(project.configurations.implementation,project.configurations.compileOnly)
//        def kaptTestConfiguration = project.configurations.create('androidTestApt').extendsFrom(project.configurations.getByName('androidTestImplementation'), project.configurations.getByName('androidTestCompileOnly'))
//        project.afterEvaluate {
//            variants.all { variant ->
//                def javaCompiler = variant.javaCompiler
//                def processorPath = (kapt1Configuration + javaCompile.classpath).asPath
//                def taskDependency = kapt1Configuration.buildDependencies
//                println("cjf ${variant} ${variant.dirName} ${javaCompiler} ${taskDependency}")
//            }
//        }

    }
}
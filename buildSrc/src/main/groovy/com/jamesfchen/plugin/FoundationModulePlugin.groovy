package com.jamesfchen.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class FoundationModulePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")
        project.plugins.apply("kotlin-kapt")
        project.plugins.apply("realm-android")
        project.android.compileSdkVersion = Config.COMPILE_SDK_VERSION
        project.android.buildToolsVersion = Config.BUILD_TOOLS_VERSION
        project.android.defaultConfig {
            minSdkVersion Config.MIN_SDK_VERSION
            targetSdkVersion Config.TARGET_SDK_VERSION
            versionCode Config.VERSION_CODE
            versionName Config.VERSION_NAME

            testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        }
        project.afterEvaluate {

            project.android.defaultConfig.javaCompileOptions.annotationProcessorOptions.arguments = [
                    "room.schemaLocation"  : "$projectDir/schemas".toString(),
                    "room.incremental"     : "true",
                    "room.expandProjection": "true"
            ]
        }


    }
}
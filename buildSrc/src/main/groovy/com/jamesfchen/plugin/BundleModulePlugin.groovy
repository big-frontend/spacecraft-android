package com.jamesfchen.plugin


import org.gradle.api.Project

class BundleModulePlugin extends BaseModulePlugin {
    @Override
    void onApply(Project project) {
        project.android{
            buildTypes {
                component{
                    initWith debug
                    debuggable true
                }
            }
        }
        project.dependencies {
//            api project.dependencies.project(path: ':framework:loader')
//            kapt "com.google.dagger:dagger-compiler:2.16"
//            kapt "com.google.dagger:dagger-android-processor:2.16"
//            annotationProcessor 'androidx.databinding:databinding-compiler:4.1.0-alpha01'
//            kapt 'com.alibaba:arouter-compiler:1.2.1'
            annotationProcessor "com.google.dagger:dagger-compiler:2.16"
            annotationProcessor "com.google.dagger:dagger-android-processor:2.16"
            annotationProcessor 'androidx.databinding:databinding-compiler:4.1.0-alpha01'
            annotationProcessor 'com.alibaba:arouter-compiler:1.2.1'
        }
    }

}


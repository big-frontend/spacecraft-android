package com.jamesfchen.plugin

import com.jamesfchen.plugin.util.P
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class  BaseModulePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def snmae=this.class.getSimpleName()
        def lastindex= snmae.indexOf('ModulePlugin')
        def type= snmae.substring(0,lastindex).toLowerCase()
        project.beforeEvaluate {
            P.info("==>>>>>>>>>>>>>>>>$type[${project.name}] start")
        }
        project.afterEvaluate {
            P.info("<<<<<<<<<<<<<<<==$type[${project.name}] end")
        }
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")
//        project.plugins.apply("kotlin-parcelize")
        project.plugins.apply("kotlin-kapt")

        project.android.compileSdkVersion = Config.COMPILE_SDK_VERSION
        project.android.buildToolsVersion = Config.BUILD_TOOLS_VERSION
        project.android{
            defaultConfig {
                minSdkVersion Config.MIN_SDK_VERSION
                targetSdkVersion Config.TARGET_SDK_VERSION
                versionCode Config.VERSION_CODE
                versionName Config.VERSION_NAME

                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
            }
//            buildTypes {
//                release {
//                    minifyEnabled false
//                    project.android.buildConfig.proguardFiles project.android.buildTypes.getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//                }
//            }
            kotlinOptions {
                jvmTarget = "1.8"
            }
            compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }
//            buildFeatures {
//                viewBinding true
//            }

            lintOptions {
                abortOnError false
            }
        }
        project.dependencies {
            implementation project.fileTree(dir: 'libs', include: ['*.jar'])
            /**
             * ===============================test start=============================
             */
            //    androidTestImplementation 'androidx.multidex:multidex-instrumentation:2.0.0',{
//        exclude group: 'com.android.support', module: 'multidex'
//    }

            // Dependencies for local unit tests
            testImplementation 'junit:junit:4.12'
//    testImplementation "org.mockito:mockito-all:1.10.19"
//    testImplementation "org.mockito:mockito-core:${rootProject.ext.mockitoVersion}"
//    testImplementation "org.mockito:mockito-inline:${rootProject.ext.mockitoVersion}"
//    testImplementation "org.hamcrest:hamcrest-all:${rootProject.ext.hamcrestVersion}"

            // Espresso dependencies
            androidTestImplementation "androidx.test.espresso:espresso-core:3.1.1"
            androidTestImplementation "androidx.test.espresso:espresso-contrib:3.1.1"
            androidTestImplementation "androidx.test.espresso:espresso-intents:3.1.1"
            androidTestImplementation "androidx.test.espresso:espresso-accessibility:3.1.1"
            androidTestImplementation "androidx.test.espresso:espresso-web:3.1.1"
            androidTestImplementation "androidx.test.espresso.idling:idling-concurrent:3.1.1"
            // The following Espresso dependency can be either "implementation"
            // or "androidTestImplementation", depending on whether you want the
            // dependency to appear on your APK's compile classpath or the test APK
            // classpath.
            androidTestImplementation "androidx.test.espresso:espresso-idling-resource:3.1.1"

            // Dependencies for Android unit tests
            androidTestImplementation "junit:junit:${project.rootProject.ext.junitVersion}"
            androidTestImplementation "org.mockito:mockito-android:${project.rootProject.ext.mockitoVersion}"
            androidTestImplementation 'com.google.dexmaker:dexmaker:1.2'
            androidTestImplementation 'com.google.dexmaker:dexmaker-mockito:1.2'
            androidTestImplementation 'androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1'

            // Android Testing Support Library's runner and rules
            androidTestImplementation 'androidx.test.ext:junit:1.1.0'
            androidTestImplementation "androidx.test:runner:1.1.1"
            androidTestImplementation "androidx.test:rules:1.1.1"
            androidTestImplementation 'androidx.test:monitor:1.1.0'

            /**
             * ===============================test end=============================
             */
        }
        onApply(project)
    }
    abstract void onApply(Project project)
}
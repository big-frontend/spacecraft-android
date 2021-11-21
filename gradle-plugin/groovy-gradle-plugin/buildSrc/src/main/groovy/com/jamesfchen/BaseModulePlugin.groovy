package com.jamesfchen

import com.jamesfchen.util.P
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
            ndkVersion '22.0.7026061'
            lintOptions {
                quiet true
                lintConfig project.file("$project.rootDir/lint.xml")
                htmlReport true
                htmlOutput project.file("$project.rootDir/lint-report-${project.name}.html")
                xmlReport true
                xmlOutput project.file("$project.rootDir/lint-report-${project.name}.xml")
                warningsAsErrors true
                abortOnError false
//                // Turns off checks for the issue IDs you specify.
//                disable 'TypographyFractions','TypographyQuotes'
//                // Turns on checks for the issue IDs you specify. These checks are in
//                // addition to the default lint checks.
//                enable 'RtlHardcoded','RtlCompat', 'RtlEnabled'
//                // To enable checks for only a subset of issue IDs and ignore all others,
//                // list the issue IDs with the 'check' property instead. This property overrides
//                // any issue IDs you enable or disable using the properties above.
//                checkOnly 'NewApi', 'InlinedApi'
//                // If set to true, turns off analysis progress reporting by lint.
//                quiet true
//                // if set to true (default), stops the build if errors are found.
//                abortOnError false
//                // if true, only report errors.
//                ignoreWarnings true
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
//        project.project.plugins.apply('maven-publish')
//        project.afterEvaluate {
//            def moduleSimpleName=''
////            for(def module:project.gradle.ext.allModules){
////                module.
////            }
//            publishing {
//                publications {
//                    // Creates a Maven publication called "release".
//                    project.release(MavenPublication) {
//                        // Applies the component for the release build variant.
//                        from components.release
//
//                        // You can then customize attributes of the publication as shown below.
//                        groupId = 'com.jamesfchen'
//                        artifactId = moduleSimpleName
//                        version = '1.0'
//                    }
//                    // Creates a Maven publication called “debug”.
//                    debug(MavenPublication) {
//                        // Applies the component for the debug build variant.
//                        from components.debug
//
//                        groupId = 'com.jamesfchen'
//                        artifactId = moduleSimpleName
//                        version = '1.0'
//                    }
//                }
//            }
//        }
    }
    abstract void onApply(Project project)
}
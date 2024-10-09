buildscript {
    repositories {
        maven(uri("$rootDir/repo"))
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://artifact.bytedance.com/repository/byteX/")
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.alibaba:arouter-register:1.0.2")
        classpath("io.github.meituan-dianping:plugin:1.2.1")
        classpath("com.tencent.tinker:tinker-patch-gradle-plugin:1.9.14.19")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.appdistribution) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.module.graph) apply true // Plugin applied to allow module graph generation
    id("io.sentry.android.gradle") version "4.1.0" apply(false)
    id("io.github.electrolytej.module-publisher-plugin") version("1.0.0") apply false
    id("io.github.electrolytej.module-assembler-rootproject-plugin") apply true

    id("electrolytej.android.application.jacoco") apply false
    id("electrolytej.android.library.jacoco") apply false
}
allprojects {
//    configurations.all {
    // don't cache changing modules at all，check for updates every build
    //一般在开发模式下，我们可以频繁的发布SNAPSHOT版本，
    // 以便让其它项目能实时的使用到最新的功能做联调；当版本趋于稳定时，再发布一个正式版本，供正式使用。
    // SNAPSHOT版本可能会频繁更新但是版本号是不变的，需要取消缓存策略
    //gradlew build --refresh-dependencies前置刷新依赖
    // [理解Maven中的SNAPSHOT版本和正式版本](https://www.cnblogs.com/huang0925/p/5169624.html)
    //[更新maven组件的坑](https://qa.1r1g.com/sf/ask/2944103851/)
//        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
//        resolutionStrategy {
    //出现两个不同版本时会报错，比如：glide:4.8 与 glide：4.11
//            failOnVersionConflict()
//            force 'com.github.bumptech.glide:glide:4.11.0'
//            force 'androidx.fragment:fragment:1.3.6'app_config
//            force 'androidx.fragment:fragment-ktx:1.3.5'
//        }

//    resolutionStrategy.eachDependency {
//        if (it.requested.group == 'com.android.support'
//                && !it.requested.name.contains('multidex')) {
//            it.useVersion
//        }
//    }
//    }
}
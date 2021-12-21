// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.oschina.net/content/groups/public/") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("./local-repo") }

        maven { url = uri("https://maven.aliyun.com/repository/google/") }
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
//        classpath "com.jamesfchen:lifecycle-plugin:1.0.0"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
//plugins {
//    id("com.jamesfchen.perf-plugin") version "1.0.0" apply false
//}
allprojects {
    repositories {
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.oschina.net/content/groups/public/") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.aliyun.com/repository/google/") }
        maven { url = uri("./local-repo") }
        mavenLocal()
        mavenCentral()
        google()
    }
//    tasks.withType(JavaCompile::class.java).configureEach { task ->
//        task.options.encoding = 'UTF-8'
//        task.sourceCompatibility = JavaVersion.VERSION_1_8
//        task.targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java).configureEach { task ->
//        task.kotlinOptions {
//            jvmTarget = '1.8'
//        }
//    }
    afterEvaluate {
    }
}

tasks.register("clean", Delete::class.java) {
    description = "Remove all the build files and intermediate build outputs"
//    delete(allprojects.map { it.buildDir })
    delete(rootProject.buildDir)
}
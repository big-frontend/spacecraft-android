//apply {
//    plugin("kotlin")
//    plugin("groovy")
//    plugin("java-gradle-plugin")
//}

plugins {
//    `kotlin-dsl`
//    id("java-gradle-plugin")
    `java-gradle-plugin`
//    id("org.gradle.kotlin.kotlin-dsl") version("1.0-rc-10")
    groovy
    kotlin("jvm") version "1.2.71"

}

buildscript {

    repositories {
        jcenter()
        google()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.2.71"))
        classpath("com.android.tools.build:gradle:3.2.0")
    }
}

//sourceSets {
//    main.kotlin.srcDirs="src/main/kotlin"
//    main {
//        java {
//            srcDirs = ['src/main/kotlin']
//        }
//    }
//}
//gradlePlugin {
//    plugins {
//        create("versionPluginForKotlin") {
////            id = "com.hawksjamesf.plugin"
//            implementationClass = "com.hawksjamesf.plugin.VersionPluginForKotlin"
//        }
//    }
//}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(kotlin("stdlib", "1.2.71"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(gradleApi())
    implementation(localGroovy())
    implementation(kotlin("reflect"))
    testCompile(kotlin("test"))
    testCompile(kotlin("test-junit"))
}

//allprojects {
//    repositories {
//        jcenter()
//        google()
//    }
//}

repositories {
    jcenter()
    google()
}





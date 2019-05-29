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
    java
    kotlin("jvm") version "1.2.71"

}

buildscript {

    repositories {
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.2.71"))
//        classpath("com.android.tools.build:gradle:3.2.0")
    }
}

sourceSets {
//    main.kotlin.srcDirs="src/main/kotlin"
//    main {
//        java {
//            srcDirs = ['src/main/util/']
//        }
//    }
    getByName("main").java.srcDir("src/main/util")
//    getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
//    getByName("debug").java.srcDirs("src/debug/kotlin")
//    getByName("main").java.srcDirs("src/main/kotlin")
//    getByName("test").java.srcDirs("src/test/kotlin")

}

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
    implementation("com.android.tools.build:gradle:3.2.1")
//    implementation("org.javassist:javassist:3.20.0-GA")
}


repositories {
    jcenter()
    google()
    mavenCentral()
}





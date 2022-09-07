import org.gradle.api.internal.classpath.ModuleRegistry
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.support.serviceOf
plugins {
    id("java-gradle-plugin")
}
val AGP_VERSION :String by project
val KOTLIN_VERSION :String by project
val groupId :String by project
gradlePlugin {
    plugins {
        create("buglyplugin") {
            id = "${groupId}.bugly-plugin"
            implementationClass = "com.jamesfchen.viapm.BuglyPlugin"
            displayName = "bugly-plugin"
            description = "bugly-plugin"
        }
    }
}
group = "${groupId}.viapm-plugin"
description = "viapm-plugin"
version = "1.0.0"
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation(files("libs\\buglyqq-upload-symbol.jar"))
    compileOnly(gradleApi())
    compileOnly("com.android.tools.build:gradle:${AGP_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}")
    implementation("com.squareup.okhttp3:okhttp:3.14.0")
    implementation("io.github.jamesfchen:base-plugin:1.2.0")
    testImplementation("junit:junit:4.13.2")

    testRuntimeOnly(
        files(
            serviceOf<ModuleRegistry>()
                .getModule("gradle-tooling-api-builders")
                .classpath
                .asFiles
                .first()))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
apply(plugin = "org.jetbrains.kotlin.jvm")
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions { jvmTarget = JavaVersion.VERSION_11.majorVersion }
}

tasks.withType<Test>().configureEach {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

//plugins {
//    id "com.gradle.plugin-publish" version "0.15.0"
//}
////发布到远程gradle plugin portal，获得approval超级慢
//pluginBundle {
//    vcsUrl = 'https://github.com/JamesfChen/spacecraft-android'
//    website = "https://github.com/JamesfChen/spacecraft-android"
//    description = "viapm gradle plugin"
//    tags = ['viapm-gradle-plugin']
//    def ver = '1.0.0'
//    plugins {
//        perfPlugin{
//            displayName = 'viapm gradle plugin'
//            tags = ['viapm-plugin']
//            version ver
//        }
//        tracePlugin {
//            displayName = 'trace plugin'
//            tags = ['trace-plugin']
//            version ver
//        }
//        buglyPlugin {
//            displayName = 'bugly plugin'
//            tags = ['bugly-plugin']
//            version ver
//        }
//        counterPlugin {
//            displayName = 'counter plugin'
//            tags = ['counter-plugin']
//            version ver
//        }
//    }
//    mavenCoordinates {
//        groupId = rootProject.groupId
//        artifactId = "viapm-plugin"
//        version = ver
//    }
//}
//

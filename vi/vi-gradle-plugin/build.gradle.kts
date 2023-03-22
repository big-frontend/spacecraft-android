import org.gradle.api.internal.classpath.ModuleRegistry
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.support.serviceOf
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    id("java-gradle-plugin")
    kotlin("plugin.serialization") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}
val AGP_VERSION :String by project
val JAVASSIST_VERSION :String by project
val KOTLIN_VERSION :String by project
val groupId :String by project
val BYTEX_VERSION :String by project
gradlePlugin {
    plugins {
        create("buglyplugin") {
            id = "${groupId}.bugly-plugin"
            implementationClass = "com.jamesfchen.vi.stability.BuglySymbolPlugin"
            displayName = "bugly-plugin"
            description = "bugly-plugin"
        }
        create("jlcplugin") {
            id = "${groupId}.jlc-plugin"
            implementationClass = "com.jamesfchen.vi.jlc.JlcPlugin"
        }
        create("viplugin") {
            id = "${groupId}.vi-plugin"
            implementationClass = "com.jamesfchen.vi.ViPlugin"
        }
    }
}
group = "${groupId}.vi-plugin"
description = "vi-plugin"
version = "1.0.0"
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    compileOnly(gradleApi())
    compileOnly("com.android.tools.build:gradle:${AGP_VERSION}")
    compileOnly("org.javassist:javassist:${JAVASSIST_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}")
    implementation("com.squareup.okhttp3:okhttp:3.14.0")
    testImplementation("junit:junit:4.13.2")
    implementation(project(":apk:matrix-commons"))
    implementation(project(":apk:matrix-arscutil"))
    testRuntimeOnly(
            files(
                    serviceOf<ModuleRegistry>()
                            .getModule("gradle-tooling-api-builders")
                            .classpath
                            .asFiles
                            .first()))
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
//    implementation("com.bytedance.android.byteX:base-plugin:${BYTEX_VERSION}")
//    implementation("com.bytedance.android.byteX:const-inline-plugin:${BYTEX_VERSION}")
//    implementation("com.bytedance.android.byteX:shrink-r-plugin:${BYTEX_VERSION}")
//    implementation("com.bytedance.android.byteX:access-inline-plugin:${BYTEX_VERSION}")
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
sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
}

tasks.register("shadowViJar", ShadowJar::class.java) {
    classifier = "all"
//    baseName = project.name
//    version = project.version.toInt()
}

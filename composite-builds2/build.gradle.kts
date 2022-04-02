// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven { url = uri("./local-repo") }
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/public") }
        maven { url = uri("https://maven.oschina.net/content/groups/public/") }
        maven { url = uri("https://maven.aliyun.com/repository/google/") }
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
allprojects {
    repositories {
        maven { url = uri("./local-repo") }
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/public") }
        maven { url = uri("https://maven.oschina.net/content/groups/public/") }
        maven { url = uri("https://maven.aliyun.com/repository/google/") }

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
}
tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
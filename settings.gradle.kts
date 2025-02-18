pluginManagement {
    repositories {
        maven(uri("$rootDir/repo"))
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google/")
        maven("https://artifact.bytedance.com/repository/byteX/")
        maven("https://artifact.bytedance.com/repository/pangle")
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    includeBuild("build-logic")
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(uri("$rootDir/repo"))
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://jitpack.io")
        maven("https://artifact.bytedance.com/repository/byteX/")
        maven("https://artifact.bytedance.com/repository/pangle")
        //不能用
//        maven{
//            url = uri("http://repo.baichuan-android.taobao.com/content/groups/BaichuanRepositories/")
//            isAllowInsecureProtocol = true
//        }
        mavenCentral()
        google()
    }

}

plugins {
    id("io.github.electrolytej.module-assembler-settings-plugin") version("1.0.0")
}



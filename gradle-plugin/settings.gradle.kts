dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}


rootProject.name = "gradle-plugin"
includeBuild("groovy-gradle-plugin") {
//    name = "g"
    dependencySubstitution {
//        substitute(module("io.github.jamesfchen:lifecycle-api")).using(project(":"))
//        substitute(module("io.github.jamesfchen:ibc-api")).using(project(":"))
//        substitute(module("io.github.jamesfchen:perf-api")).using(project(":"))
    }
}
include(
    ":app",
    ":bundle1",
    ":bundle2",
)
include(":common")
include(":loader")

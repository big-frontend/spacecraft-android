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
//    dependencySubstitution {
//        substitute(module("io.jamesfchen:api")).using(project(":"))
//    }
}
include(
    ":app",
    ":bundle1",
    ":bundle2",
)

pluginManagement {
    repositories {
        maven {
            url =uri("./local-repo")
        }
        gradlePluginPortal()
    }
    plugins {
        id("com.github.jamesfchen.perf-plugin")  version "1.0.0"
    }
}
rootProject.name = "groovy-gradle-plugin"
include(
    ":base-plugin",
    ":perf:annotations", ":perf:complier", ":perf:perf-plugin",":perf:perf-api",
    ":ksp", ":kcp",
    ":counter-plugin",
    ":bugly-plugin",
    ":temple-plugin",
    ":proguard-plugin"
)

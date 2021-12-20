//pluginManagement {
//    repositories {
//        maven {
//            url './local-repo'
//        }
//        gradlePluginPortal()
//    }
//    plugins {
//        id 'com.jamesfchen.lifecycle-plugin'  version "1.0.0"
//    }
//}
rootProject.name = "groovy-gradle-plugin"
include(
    ":base-plugin",
    ":perf:annotations", ":perf:complier", ":perf:perf-plugin",
    ":lifecycle:api", ":lifecycle:lifecycle-plugin",
    ":ibc:api", ":ibc:complier", "ibc:ibc-plugin", ":ibc:annotations",
    ":ksp", ":kcp",
    ":counter-plugin",
    ":bugly-plugin",
    ":temple-plugin",
    ":proguard-plugin"
)

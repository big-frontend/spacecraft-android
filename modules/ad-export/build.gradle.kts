plugins {
    id("io.github.electrolytej.api-plugin")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.electrolytej.export"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
apply {
//    plugin("kotlin")
    plugin("groovy")
}

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `java-library`
}
//gradlePlugin {
//    plugins {
//        create("foundation") {
//            id = "foundation-module"
//            implementationClass = "FoundationModulePlugin"
//        }
//        create("bundle") {
//            id = "bundle-module"
//            implementationClass = "BundleModulePlugin"
//        }
//    }
//}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(files("libs/buglySymbolAndroid.jar"))
    implementation(gradleKotlinDsl())
    implementation(localGroovy())
    implementation(gradleApi())
    implementation(kotlin("stdlib", "1.2.21"))
    implementation("com.android.tools.build:gradle:3.5.4")
    implementation("org.ow2.asm:asm-commons:5.1")
    implementation("org.ow2.asm:asm:5.1")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
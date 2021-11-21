buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
apply {
    plugin("kotlin")
//    plugin("groovy")
}

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `java-library`
}
gradlePlugin {
    plugins {
        create("perfplugin") {
            id = "com.jamesfchen.perfplugin"
            implementationClass = "com.jamesfchen.plugin.PerfPlugin"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(gradleKotlinDsl())
//    implementation(localGroovy())
    implementation(gradleApi())
    implementation(kotlin("stdlib", "1.2.21"))
    implementation("com.android.tools.build:gradle:7.1.0-alpha01")
//    implementation("org.ow2.asm:asm-commons:5.1")
//    implementation("org.ow2.asm:asm:5.1")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
configurations {
    compileOnly
}

sourceSets {
    main {
        compileClasspath += configurations.compileOnly
    }
}
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
//    id("com.jamesfchen.perfplugin")
}

//configure<com.jamesfchen.plugin.PerfExtension> {
//    disable.set(true)
//}
android {
    compileSdk = 29

    defaultConfig {
        applicationId = "com.jamesfchen.spacecraftplugin"
        minSdk = 21
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        create("staging") {
//            ...
//        }
    }
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding =true
    }


//    flavorDimensions += listOf("tier")
//    productFlavors {
//        create("free") {
//            dimension = "tier"
//            applicationId = "com.example.myapp.free"
//        }
//
//        create("paid") {
//            dimension = "tier"
//            applicationId = "com.example.myapp.paid"
//        }
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation(project(":lib"))
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
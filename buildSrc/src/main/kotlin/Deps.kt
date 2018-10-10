object deps {
    //ext {
//    compileSdkVersion = 26
//    buildToolsVersion = '27.0.0'
//    minSdkVersion = 17
//    targetSdkVersion = 26
//    versionCode = 2
//    versionName = "2.0"
//
//    // App dependencies
//    supportLibraryVersion = '27.0.2'
//    rxjavaVersion = '2.1.10'
////    rxjavaVersion = '1.1.7'
//    rxandroidVersion = '1.2.1'
//    junitVersion = '4.12'
//    glideVersion = '3.7.0'
//    retrofitVersion = '2.3.0'
//    espressoVersion = '3.0.1'
//    runnerVersion = '1.0.0'
//    mockitoVersion = '1.10.19'
//    hamcrestVersion = '1.3'
//
//}
    object plugin {
        val gradle = "com.android.tools.build:gradle:3.1.0-alpha09"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.21"
    }

    object kotlin {
        val stdlibJre7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:1.2.21"
    }

    object ext {

        val compileSdkVersion = 26
        val buildToolsVersion = "28.0.2"
        val minSdkVersion = 17
        val targetSdkVersion = 26
        val versionCode = 2
        val versionName = "2.0"
        val kotlinVersion = "1.2.31"

        //    // App dependencies
        val supportLibraryVersion = "27.0.2"
        val rxjavaVersion = "2.1.10"
        val rxandroidVersion = "1.2.1"
        val junitVersion = "4.12"
        val glideVersion = "3.7.0"
        val retrofitVersion = "2.3.0"
        val espressoVersion = "3.0.1"
        val runnerVersion = "1.0.0"
        val mockitoVersion = "1.10.19"
        val hamcrestVersion = "1.3"
        val roomVersion = "1.1.1"

        object support {
            val compat = "com.android.support:appcompat-v7:27.0.2"
            val constraintLayout = "com.android.support.constraint:constraint-layout:1.0.2"
        }

        object test {
            val junit = "junit:junit:4.12"
            val runner = "com.android.support.test:runner:1.0.1"
            val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.1"
        }
    }
}
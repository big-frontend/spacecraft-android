plugins {
    id("java-library")
    kotlin("jvm")
}
//apply plugin: 'com.android.lint'
//lintOptions {
//    htmlReport true
//    htmlOutput file("lint-report.html")
//    textReport true
//    absolutePaths false
//    ignoreTestSources true
//}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:30.3.0")
    compileOnly("com.android.tools.lint:lint-checks:30.3.0")
    val  KOTLIN_VERSION :String by project
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler:${KOTLIN_VERSION}")
}
//jar {
//    manifest {
//        attributes("Lint-Registry": "com.jamesfchen.rules.MyIssueRegistry")
//    }
//}
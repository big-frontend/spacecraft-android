package com.jamesfchen


import org.gradle.api.Project

class FoundationModulePlugin extends BaseModulePlugin {

    @Override
    void onApply(Project project) {
//        def moduleConfig = project.extensions.create("moduleConfig", ModuleExtension)
        project.android.sourceSets {
            if (project.hasProperty("srcDirs")) {
                main {
                    java.excludes = [
                            '**/build/**',
                    ]
                    project.ext.srcDirs.forEach {
                        assets.srcDirs += "$project.projectDir/$it/main/assets"
                        aidl.srcDirs += "$project.projectDir/$it/main/aidl"
                        res.srcDirs += "$project.projectDir/$it/main/res-frame-animation"
                        res.srcDirs += "$project.projectDir/$it/main/res"
                        java.srcDirs += "$project.projectDir/$it/main/java"

                    }
                }
                androidTest {
                    project.ext.srcDirs.forEach {
                        assets.srcDirs += "$project.projectDir/$it/androidTest/assets"
                        aidl.srcDirs += "$project.projectDir/$it/androidTest/aidl"
                        res.srcDirs += "$project.projectDir/$it/androidTest/res"
                        java.srcDirs += "$project.projectDir/$it/androidTest/java"

                    }
                }
                test {
                    project.ext.srcDirs.forEach {
                        java.srcDirs += "$project.projectDir/$it/test/java"
                    }

                }
            }
        }
    }
}
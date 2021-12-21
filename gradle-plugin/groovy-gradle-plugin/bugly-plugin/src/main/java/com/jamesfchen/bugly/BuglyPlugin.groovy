/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jamesfchen.bugly

import com.jamesfchen.util.P
import Plugin
import Project

/**
 * {@code BuglyPlugin} is a gradle plugin for uploading symtab files to Bugly platform.
 *
 * <p>The term "symtab file" is an abbreviation of "symbol table file" that includes mapping
 * files (*.txt) created by Proguard and symbol files (*.symbol) created by "Symtab Tool for
 * Android" of Bugly which can be downloaded from http://bugly.qq.com/whitebook.
 *
 * <p>This plugin will create a task named "upload${variantName}SymtabFile".
 * ({@code "variantname"} means the name of variant. e.g., "Release") for doing following two
 * things:
 * <p>1. Create symbol files of SO files (*.so) created by Android NDK if the project has
 * native code.
 * <p>2. Upload symtab files to Bugly platform implemented through HttpClient.
 * <p>The "upload${variantName}SymtabFile"  task will only be activated if
 * the variant is "release". And if the project doesn't enable code obfuscation and doesn't
 * have any native code, the task will do nothing.
 *
 * <p>The plugin should be configured through the following required properties:
 * <p>{@code appId}: app ID of Bugly platform.
 * <p>{@code appKey}: app Key of Bugly platform.
 * <p>Other optional properties:
 * <p>{@code execute}: switch for controlling execution of "upload${variantName}SymtabFile".
 * <p>{@code upload}: switch for uploading symtab files.
 * <p>{@code outputDir}: Directory where symbol files would be output to.
 *
 * <p>More details can be read at http://bugly.qq.com/androidsymbol.
 *
 * @author JalenChen
 */
class BuglyPlugin implements Plugin<Project> {
    private Project project = null

    @Override
    void apply(Project project) {
        P.info("BuglyPlugin apply start")
        this.project = project
        project.extensions.create("bugly", BuglyPluginExtension)
        if (!project.hasProperty("android")) {
            return
        }
        if (project.android.hasProperty("applicationVariants")) { // For android application.
            println "BuglyPlugin apply applicationVariants"
            project.android.applicationVariants.all { variant ->
                createTask(variant)
            }
        } else if (project.android.hasProperty("libraryVariants")) { // For android library.
            println "BuglyPlugin apply libraryVariants"
            project.android.libraryVariants.all { variant ->
                createTask(variant)
            }
        }
        P.info("BuglyPlugin apply end")
    }

    def createTask(Object variant) {
        String variantName = variant.name.capitalize()
        ReportMappingTask mappingTask = project.tasks.create("reportMapping${variantName}", ReportMappingTask)
        mappingTask.doFirst {
            mappingTask.variant = variant
        }

        ReportSoTask soTask = project.tasks.create("reportSo${variantName}", ReportSoTask)
        soTask.doFirst {
            soTask.variant = variant
        }
        if (containTask("package${variantName}JniLibs")) {
            soTask.dependsOn project.tasks["package${variantName}JniLibs"]
        } else if (containTask("externalNativeBuild${variantName}")) {
            soTask.dependsOn project.tasks["externalNativeBuild${variantName}"]
        } else if (containTask("externalNativeBuild${variantName}")) {
            mappingTask.dependsOn project.tasks["package${variantName}"]
        }
    }

    boolean containTask(String taskName) {
        for (def task in project.tasks) {
            if (task.name == taskName) {
                return true
            }
        }
        return false
    }
}
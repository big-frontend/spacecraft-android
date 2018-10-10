package com.hawksjamesf.plugin

import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project

open class VersionPluginForKotlin : Plugin<Project> {
    override fun apply(project: Project?) {
//        var extension = project?.extensions?.create("versionPluginForKotlin",VersionPluginExtionForKotlin::class.java)
//            project?.extensions?.getByName("android")?
        val extension = project?.extensions?.run {
            create("versionPluginForKotlin", VersionPluginExtensionForKotlin::class.java)
        }

        project?.afterEvaluate {
            var template = extension?.fileFormat
            var engine = SimpleTemplateEngine()

            println("---> version plugin for kotlin\n" +
                    "${extension?.buildTypeMatcher}\n" +
                    "${extension?.fileFormat}\n<---")

//            project.android.applicationVariants.matching({
//            }).all {
//            }

//            project.android.applicationVariants.matching({
//                it.buildType.name.matches(versionPlugin.buildTypeMatcher)
//            }).all {
//                variant ->

//                var map = [
//                        'appName'    : project.name,
//                'project'    : project.rootProject.name,
//                'buildType'  : variant.buildType.name,
//                'versionName': variant.versionName,
//                'versionCode': variant.versionCode,
//
//                ]
//                var fileName = engine.createTemplate(template).make(map).toString()
//
//                variant.outputs.all {
//                    outputFileName = fileName + '.apk'
//
//
//                }

//            }

        }
    }

}
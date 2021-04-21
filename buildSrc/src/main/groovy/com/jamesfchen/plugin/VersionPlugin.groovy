package com.jamesfchen.plugin

import com.jamesfchen.plugin.util.P
import groovy.text.SimpleTemplateEngine
import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionPlugin implements Plugin<Project> {
    void apply(Project project) {
        P.info("VersionPlugin apply start")
        def versionPlugin = project.extensions.create("versionPlugin", VersionPluginExtension)

        //rename app name
        //$appName-v_$versionName-c_$versionCode
        project.afterEvaluate {
            def template = versionPlugin.fileFormat
            def engine = new SimpleTemplateEngine()
            println("--> version plugin for groovy \n ${versionPlugin.buildTypeMatcher}\n ${versionPlugin.fileFormat}\n<---")
            project.android.applicationVariants.matching({
                it.buildType.name.matches(versionPlugin.buildTypeMatcher)
            }).all { variant ->

                def map = [
                        'appName'    : project.name,
                        'project'    : project.rootProject.name,
                        'buildType'  : variant.buildType.name,
                        'versionName': variant.versionName,
                        'versionCode': variant.versionCode,
                        'releaseTime': releaseTime(),

                ]
                def fileName = engine.createTemplate(template).make(map).toString()

                variant.outputs.all {
                    outputFileName = fileName + '.apk'


                }

            }

        }
        P.info("VersionPlugin apply end")
    }

    def releaseTime() {
        return new Date().format("MM-dd", TimeZone.getTimeZone("UTC"))
    }
}
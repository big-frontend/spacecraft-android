package com.jamesfchen.plugin


import org.gradle.api.Project

class BundleModulePlugin extends BaseModulePlugin {
    def  versionPlugin(Closure<VersionPluginExtension> closure/*从build.gradle获取*/){
        def c = new VersionPluginExtension()//从project.extensions.create获取
        closure.setDelegate(c)//委托代理优先
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)//Groovy的闭包有this、owner、delegate三个属性
        return closure
    }
    @Override
    void onApply(Project project) {

//        project.plugins.apply("com.jamesfchen.versionplugin")
//        project.extensions.inject( kapt{
//            arguments {
//                arg("AROUTER_MODULE_NAME", project.getName())
//            }
//        })
//        project.extensions.add(VersionPlugin.class,versionPlugin {
//                    buildTypeMatcher = 'debug'
//                    //        '$appName/project/buildType-v_$versionName-c_$versionCode'
//                    //        fileFormat = '$appName-v_$versionName-c_$versionCode'
//                    fileFormat = '$project-$appName-$buildType-v_$versionName-c_$versionCode-time_$releaseTime'
//                })
        project.dependencies {
            api project.dependencies.project(path: ':framework:loader')
//            kapt "com.google.dagger:dagger-compiler:2.16"
//            kapt "com.google.dagger:dagger-android-processor:2.16"
//            annotationProcessor 'androidx.databinding:databinding-compiler:4.1.0-alpha01'
//            kapt 'com.alibaba:arouter-compiler:1.2.1'
            annotationProcessor "com.google.dagger:dagger-compiler:2.16"
            annotationProcessor "com.google.dagger:dagger-android-processor:2.16"
            annotationProcessor 'androidx.databinding:databinding-compiler:4.1.0-alpha01'
            annotationProcessor 'com.alibaba:arouter-compiler:1.2.1'
        }

    }

}


package com.jamesfchen.viapm


import com.android.build.gradle.AppExtension
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File

interface BuglyExtension {
    val appId: Property<String>
    val appKey: Property<String>
}

private const val SYMBOL_UPLOAD_URL = "https://api.bugly.qq.com/openapi/file/upload/symbol"

data class SymbolReq(
    val appId: String,
    val appKey: String,
    val packageName: String,
    val version: String,
    val file: File,
    val channel: String = "",
    val isMappingFile: Boolean = true
)

private fun postSymbol(r: SymbolReq) {
    val url = buildString {
        append(SYMBOL_UPLOAD_URL)
        append("?app_id=${r.appId}")
        append("&app_key=${r.appKey}")
    }
    println(r)
    println(r.file.name)
    val form =
        MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("api_version", "1")
            .addFormDataPart("app_id", r.appId)
            .addFormDataPart("app_key", r.appKey)
            .addFormDataPart("channel", r.channel)
            .addFormDataPart("symbolType", if (r.isMappingFile) "1" else "3")
            .addFormDataPart("bundleId", r.packageName)
                .addFormDataPart("productVersion", r.version)
            .addFormDataPart("fileName", r.file.name)
            .addFormDataPart(
                "file",
                r.file.name,
                RequestBody.create(MediaType.parse("text/plain"), r.file)
            )
            .build()
    Api.postForm(url, form)
}

class BuglyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        if (!project.plugins.hasPlugin("com.android.application")) throw  IllegalArgumentException("bugly plugin必须在app模块中配置")
        val buglyExtension = project.extensions.create("bugly", BuglyExtension::class.java)
        //所有project 加载完成之后
        project.gradle.projectsLoaded {}
        //所有project evaluate完成之后
        project.gradle.projectsEvaluated {}
        project.afterEvaluate {
            val appExtension = project.extensions.getByType(AppExtension::class.java)
            appExtension.applicationVariants.all { variant ->
                val variantName = variant.name.capitalize()//DevPhoneRelease
                project.tasks.create("report${variantName}Mapping")
                    .apply { group = Constants.TASK_GROUP }
                    .doFirst {
                        checkNotNull(buglyExtension.appId)
                        checkNotNull(buglyExtension.appKey)
                    }.doLast {
                        val flavorName = variant.flavorName // devPhone
                        val buildTypeName = variant.buildType.name // release
                        val versionName = appExtension.defaultConfig.versionName
                        val versionCode = appExtension.defaultConfig.versionCode
                        val mappingFile = variant.mappingFileProvider.get().singleFile
                        postSymbol(
                            SymbolReq(
                                buglyExtension.appId.get(),
                                buglyExtension.appKey.get(),
                                appExtension.defaultConfig.applicationId!!,
                                versionName!!,
                                mappingFile
                            )
                        )

                    }
//                ReportSoTask soTask = project . tasks . create ("reportSo${variantName}", ReportSoTask)
//                soTask.doFirst {
//                    soTask.variant = variant
//                }
//                soTask.group = Constants.TASK_GROUP
//                if (containTask(project.rootProject, "package${variantName}JniLibs")) {
//                    soTask.dependsOn project . tasks ["package${variantName}JniLibs"]
//                } else if (containTask(project.rootProject, "externalNativeBuild${variantName}")) {
//                    soTask.dependsOn project . tasks ["externalNativeBuild${variantName}"]
//                }
            }
            project.tasks.create("reportAllMapping")
                .apply { group = Constants.TASK_GROUP }
                .doFirst {
                    checkNotNull(buglyExtension.appId)
                    checkNotNull(buglyExtension.appKey)
                }.doLast {

                }
        }

    }
}
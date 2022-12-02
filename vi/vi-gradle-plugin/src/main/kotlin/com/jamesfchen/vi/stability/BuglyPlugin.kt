package com.jamesfchen.vi.stability


import com.android.build.gradle.api.ApplicationVariant
import com.jamesfchen.vi.Api
import com.tencent.bugly.symtabtool.android.SymtabToolAndroid
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File


interface BuglyExtension {
    val appId: Property<String>
    val appKey: Property<String>
}

class BuglyPlugin : BaseSymbolPlugin() {
    companion object {
        private const val SYMBOL_UPLOAD_URL = "https://api.bugly.qq.com/openapi/file/upload/symbol"
    }

    lateinit var buglyExtension: BuglyExtension
    override fun apply(project: Project) {
        super.apply(project)
//        if (!project.plugins.hasPlugin("com.android.application")) throw  IllegalArgumentException("bugly plugin必须在app模块中配置")
        buglyExtension = project.extensions.create("bugly", BuglyExtension::class.java)
        //所有project 加载完成之后
        project.gradle.projectsLoaded {}
        //所有project evaluate完成之后
        project.gradle.projectsEvaluated {}


    }

    override fun uploadMapping(variant: ApplicationVariant, mappingFile: File) {
        checkNotNull(buglyExtension.appId)
        checkNotNull(buglyExtension.appKey)
        val flavorName = variant.flavorName // devPhone
        val buildTypeName = variant.buildType.name // release
        val versionCode = variant.versionCode
        val versionName = variant.versionName
        val applicationId = variant.applicationId
        postSymbol(
            SymbolReq(
                buglyExtension.appId.get(),
                buglyExtension.appKey.get(),
                applicationId,
                versionName,
                mappingFile
            )
        )
    }

    override fun uploadSymtabs(variant: ApplicationVariant, symtabs: List<File>) {
        checkNotNull(buglyExtension.appId)
        checkNotNull(buglyExtension.appKey)
        println("${symtabs.size} $symtabs")
        val versionName = variant.versionName
        val applicationId = variant.applicationId
        symtabs.forEach { symtab ->
            postSymbol(
                SymbolReq(
                    buglyExtension.appId.get(),
                    buglyExtension.appKey.get(),
                    applicationId,
                    versionName,
                    createSymbolFile(symtab.absolutePath)
                )
            )
        }
    }

    fun createSymbolFile(symtab: String): File {
        SymtabToolAndroid.main(arrayOf("-i", symtab))
        return File(SymtabToolAndroid.symtabFileName)
    }

    private fun postSymbol(r: SymbolReq) {
        val url = buildString {
            append(SYMBOL_UPLOAD_URL)
            append("?app_id=${r.appId}")
            append("&app_key=${r.appKey}")
        }
        println(r)
        println(r.file.name)
        val form =
            MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("api_version", "1")
                .addFormDataPart("app_id", r.appId).addFormDataPart("app_key", r.appKey)
                .addFormDataPart("channel", r.channel)
                .addFormDataPart("symbolType", if (r.isMappingFile) "1" else "3")
                .addFormDataPart("bundleId", r.packageName)
                .addFormDataPart("productVersion", r.version)
                .addFormDataPart("fileName", r.file.name).addFormDataPart(
                    "file", r.file.name, RequestBody.create(MediaType.parse("text/plain"), r.file)
                ).build()
        Api.postForm(url, form)
    }

    data class SymbolReq(
        val appId: String,
        val appKey: String,
        val packageName: String,
        val version: String,
        val file: File,
        val channel: String = "",
        val isMappingFile: Boolean = true
    )
}





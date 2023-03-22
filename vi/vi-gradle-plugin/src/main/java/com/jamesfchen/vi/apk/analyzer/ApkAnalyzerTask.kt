package com.jamesfchen.vi.apk.analyzer

import com.android.build.gradle.api.BaseVariant
import com.jamesfchen.vi.apk.minify.ApkAnalyzerExtension
import com.jamesfchen.vi.findApkAnalyzer
import com.jamesfchen.vi.findRTxtFile
import com.jamesfchen.vi.findToolnm
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

abstract class ApkAnalyzerTask @Inject constructor(
    @Internal val extension: ApkAnalyzerExtension,
    @Internal val variant: BaseVariant
) : DefaultTask() {
    @TaskAction
    fun analyze() {
        var mappingFile:File? = null
        try {
            mappingFile = variant.mappingFileProvider?.get()?.singleFile
        }catch (_: Exception){ }
        val f = project.file(extension.configPath)
        f.inputStream().bufferedReader().use { reader ->
            val config = JSONObject(reader.readText())
            config.put("--apk", variant.outputs.first().outputFile.absolutePath)
            if (mappingFile?.exists() == true){ config.put("--mappingTxt", mappingFile.absolutePath) }
            config.put("--output", "${project.buildDir}/outputs/apk-checker-result")
            val options = config.optJSONArray("options")
            for (i in 0 until options.length()) {
                val o = options.getJSONObject(i)
                if (o["name"] == "-checkMultiSTL") {
                    o.put("--toolnm", project.findToolnm().absolutePath)
                } else if (o["name"] == "-unusedResources") {
                    o.put("--rTxt", project.findRTxtFile(variant).absolutePath)
                } else if (o["name"] == "-unstrippedSo") {
                    o.put("--toolnm", project.findToolnm().absolutePath)
                }
            }
            f.outputStream().bufferedWriter().use { writer ->
                writer.write(config.toString(4))
            }
        }
        project.javaexec {
            it.main = "-version"
        }
        //https://bugs.openjdk.org/browse/JDK-8211795
        //ArrayIndexOutOfBoundsException in PNGImageReader的问题在"11.0.16被fixed
        project.javaexec {
            it.main = "-jar"
            it.args = listOf(
                project.findApkAnalyzer().absolutePath,
                "--config",
                extension.configPath
            )
        }
    }
}
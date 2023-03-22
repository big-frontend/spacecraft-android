package com.jamesfchen.vi.apk.minify

import com.android.build.gradle.api.BaseVariant
import com.android.utils.FileUtils
import com.jamesfchen.vi.findApksigner
import com.jamesfchen.vi.findZipalign
import com.tencent.matrix.javalib.util.Log
import com.tencent.matrix.plugin.compat.AgpCompat
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.PrintWriter
import javax.inject.Inject
/**
 * 无用资源、重复资源、混淆资源
 */
open class MinifyTask @Inject constructor(
    @Internal val variant: BaseVariant,
    @Internal val minifyExtension: MinifyExtension,
    @Internal val resourceMinifyExtension: ResourceMinifyExtension,
    @Internal val imageExtension: ImageExtension,
    @Internal val soMinifyExtension: SoMinifyExtension,
    @Internal val codeMinifyExtension: CodeMinifyExtension,
) : DefaultTask() {
    companion object {
        const val TAG = "MinifyTask"
        const val RES_PATH_MAPPING_PREFIX = "res path mapping:"
        const val RES_ID_MAPPING_PREFIX = "res id mapping:"
        const val RES_FILE_MAPPING_PREFIX = "res file mapping:"

        const val BACKUP_DIR_NAME = "shrinkResourceBackup"
        const val SHRUNK_R_TXT_FILE = "R_shrinked.txt"
        const val RES_GUARD_MAPPING_FILE_NAME = "resguard-mapping.txt"
    }

    val signingConfig = AgpCompat.getSigningConfig(variant)
    val pathOfZipAlign by lazy {
        val z = project.findZipalign()
        // Validate path of Zipalign tool
        if (!z.exists()) {
            throw GradleException("Need zipalign apk but $z is not exist!")
        }
        return@lazy z.absolutePath
    }
    private val pathOfApkSigner by lazy {
        // Validate path of signing
        val apksigner= project.findApksigner()
        if (!apksigner.exists()) {
            throw GradleException("Need signing apk but $apksigner is not exist!")
        }
        return@lazy apksigner.absolutePath
    }
    init {
        if (AgpCompat.getSigningConfig(variant) == null) {
            throw GradleException("Need signing apk but signingConfig is not specified!")
        }
    }

    private val resultOfObfuscatedDirs = HashMap<String, String>()
    private val resultOfObfuscatedFiles = HashMap<String, String>()
    @TaskAction
    fun minify() {
        variant.outputs.forEach { output ->
            val startTime = System.currentTimeMillis()
            minify(output.outputFile)
            Log.i(TAG, "cost time %f s", (System.currentTimeMillis() - startTime) / 1000.0f)
        }
    }
    lateinit var  apkAnalyzer: ApkAnalyzer

    private fun minify(originalApkFile: File) {
        signingConfig ?: return
        apkAnalyzer = ApkAnalyzer(project,variant,resourceMinifyExtension,soMinifyExtension)
        apkAnalyzer.calculate(originalApkFile)
        // Remove unused resources.
        val startTime = System.currentTimeMillis()
        val shrunkApkFile = originalApkFile.minify(
            project,minifyExtension,
            apkAnalyzer.mapOfResources,
            apkAnalyzer.mapOfStyleables,
            apkAnalyzer.mapOfResourcesGonnaRemoved,
            apkAnalyzer.mapOfDuplicatesReplacements,
            apkAnalyzer.mapOfObfuscatedNames,

            resultOfObfuscatedDirs,
            resultOfObfuscatedFiles
        )
//        Log.i(TAG, "remove duplicated resources & unused resources success? %s, cost time %f s", shrunkApkFile?.absoluteFile, (System.currentTimeMillis() - startTime) / 1000.0f)
        if (shrunkApkFile != null) {
            // Zip align
            if (minifyExtension.useZipAlign) {
                val alignedApk = originalApkFile.parentFile.canonicalPath + File.separator + originalApkFile.name.substring(0, originalApkFile.name.indexOf(".")) + "_aligned.apk"
                Log.i(TAG, "Zipalign apk...")
                ApkUtil.zipAlignApk(shrunkApkFile.absolutePath, alignedApk, pathOfZipAlign)
                shrunkApkFile.delete()
                FileUtils.copyFile(File(alignedApk), shrunkApkFile)
                File(alignedApk).delete()
            }

            // Signing apk
            if (minifyExtension.useApkSign) {
                Log.i(TAG, "Signing apk...")
                ApkUtil.signApk(shrunkApkFile.absolutePath, pathOfApkSigner, signingConfig)
            }
            val fileOfBackup = File(project.buildDir, "outputs")
                .resolve(BACKUP_DIR_NAME)
                .resolve(originalApkFile.name.substring(0, originalApkFile.name.indexOf(".")))

            fileOfBackup.mkdirs()
            // Backup original apk and swap shrunk apk
            FileUtils.copyFile(originalApkFile, File(fileOfBackup, "backup.apk"))
            val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
            if (isWindows) {
                System.gc();
                Thread.sleep(2000);
            }
            originalApkFile.delete()
            FileUtils.copyFile(shrunkApkFile, originalApkFile)
            if (isWindows) {
                System.gc();
                Thread.sleep(2000);
            }
            shrunkApkFile.delete()

            saveTxt(pathOfBackup = fileOfBackup.absolutePath)
        }
    }

    fun saveTxt(pathOfBackup: String) {
        // Save shrunk R.txt
        saveShrunkRTxt(pathOfBackup = pathOfBackup)
        // Write resource mapping to resguard-mapping.txt
        writeResGuardMappingFile(pathOfBackup)
    }

    private fun saveShrunkRTxt(pathOfBackup: String) {
        // Modify R.txt to delete the removed resources
        if (apkAnalyzer.mapOfResourcesGonnaRemoved.isNotEmpty()) {
            val styleableIterator = apkAnalyzer.mapOfStyleables.keys.iterator()
            while (styleableIterator.hasNext()) {
                val styleable = styleableIterator.next()
                val attrs = apkAnalyzer.mapOfStyleables[styleable]
                var j = 0
                if (attrs != null) {
                    for (i in 0 until attrs.size) {
                        j = i
                        if (!apkAnalyzer.mapOfResourcesGonnaRemoved.containsValue(attrs[i].right)) {
                            break
                        }
                    }
                    if (attrs.isNotEmpty() && j == attrs.size) {
                        Log.i(TAG, "removed styleable $styleable")
                        styleableIterator.remove()
                    }
                }
            }
            val newResTxtFile = pathOfBackup + File.separator + SHRUNK_R_TXT_FILE
            ApkUtil.shrinkResourceTxtFile(
                newResTxtFile,
                apkAnalyzer.mapOfResources,
                apkAnalyzer.mapOfStyleables
            )

            //Other plugins such as "Tinker" may depend on the R.txt file, so we should not modify R.txt directly .
            //new File(newResTxtFile).renameTo(resTxtFile)
        }
    }


    fun writeResGuardMappingFile(pathOfBackup: String) {
        if (!resourceMinifyExtension.obfuscateArsc) return
        val resGuardMappingFile = File(pathOfBackup, RES_GUARD_MAPPING_FILE_NAME)
        resGuardMappingFile.createNewFile()
        val fileWriter = PrintWriter(resGuardMappingFile)
        fileWriter.println(RES_PATH_MAPPING_PREFIX)
        if (resultOfObfuscatedDirs.isNotEmpty()) {
            for (srcDir in resultOfObfuscatedDirs.keys) {
                fileWriter.println("    " + srcDir + " -> " + resultOfObfuscatedDirs[srcDir])
            }
        }
        fileWriter.println(RES_ID_MAPPING_PREFIX)
        if (apkAnalyzer.mapOfObfuscatedNames.isNotEmpty()) {
            for (srcRes in apkAnalyzer.mapOfObfuscatedNames.keys) {
                fileWriter.println("    " + srcRes + " -> " + apkAnalyzer.mapOfObfuscatedNames[srcRes])
            }
        }
        fileWriter.println(RES_FILE_MAPPING_PREFIX)
        if (resultOfObfuscatedFiles.isNotEmpty()) {
            for (srcFile in resultOfObfuscatedFiles.keys) {
                fileWriter.println("    " + srcFile + " -> " + resultOfObfuscatedFiles[srcFile])
            }
        }
        fileWriter.close()
    }
}
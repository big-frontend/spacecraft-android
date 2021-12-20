package com.jamesfchen.kotlin

import androidx.annotation.CallSuper
import com.android.build.api.transform.*
import java.io.File
import java.io.IOException
import java.util.jar.JarFile

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jul/09/2021  Fri
 */
abstract class AbsScanClassTransform : Transform(), IScanClass {
    override fun isIncremental() = false

    @CallSuper
    @Throws(TransformException::class, InterruptedException::class, IOException::class)
    override fun transform(transformInvocation: TransformInvocation) {
        val outputProvider = transformInvocation.outputProvider
        onScanBegin()
        transformInvocation.inputs.forEach { input: TransformInput ->
            //源代码编译之后的class目录
            for (directoryInput in input.directoryInputs) {
                val srcDir: File = directoryInput.file
                val destDir: File = outputProvider.getContentLocation(
                    directoryInput.name, directoryInput.contentTypes,
                    directoryInput.scopes, Format.DIRECTORY
                )
                var rootPath = destDir.absolutePath
                if (!rootPath.endsWith(File.separator)) {
                    rootPath += File.separator
                }
                srcDir.copyRecursively(destDir,overwrite = true)
                destDir.walk().filter {
                    it.name.endsWith(".class") && !(it.name == "R.class" || it.name.startsWith("R\$") || it.name == "BuildConfig.class")
                }.forEach {
                    val classPath = it.absolutePath.replace(rootPath, "")
                    val canonicalName = classPath.split("""\\.""")[0].replace(File.separator, ".")
                    onScanClassInDir(ClassInfo(destDir, it, canonicalName))
                }

            }
            //jar包，jar包或者aar包
            for (jarInput in input.jarInputs) {
                val destJar = outputProvider.getContentLocation(
                    jarInput.name,
                    jarInput.contentTypes,
                    jarInput.scopes,
                    Format.JAR
                )
                jarInput.file.copyTo(destJar,overwrite = true)
                val jarFile = JarFile(destJar)
                jarFile.entries().iterator().forEach {
                    val inputStream = jarFile.getInputStream(it)
                    val canonicalName = it.name.split("""\\.""")[0].replace("/", ".")
                    val fileName = it.name.substring(it.name.lastIndexOf("/") + 1)
                    if (fileName.endsWith(".class")
                        && !(fileName == "R.class" || fileName.startsWith("R\$") || fileName == "BuildConfig.class")
                    ) {
                        onScanClassInJar(ClassInfo(destJar, inputStream, canonicalName))
                    }
                    inputStream.close()
                }
                jarFile.close()

            }
        }
        onScanEnd()
    }
}
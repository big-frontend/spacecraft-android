package com.jamesfchen.kotlin

import androidx.annotation.CallSuper
import com.android.build.api.transform.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jul/10/2021  Sat
 */
abstract class AbsInsertCodeTransform : Transform(), IInsertCode {

    override fun isIncremental(): Boolean {
        return false
    }

    @CallSuper
    @Throws(TransformException::class, InterruptedException::class, IOException::class)
    override fun transform(transformInvocation: TransformInvocation) {
        val outputProvider = transformInvocation.outputProvider
        onInsertCodeBegin()
        transformInvocation.inputs.forEach { input: TransformInput ->
            //源代码编译之后的class目录
            for (directoryInput in input.directoryInputs) {
                val srcDir = directoryInput.file
                val destDir = outputProvider.getContentLocation(
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
                    it.inputStream().use { fis ->
                        val codes = onInsertCode(destDir, fis, canonicalName)
                        if (codes?.isNotEmpty() == true) {
                            it.outputStream().use { fos ->
                                fos.write(codes)
                            }
                        }
                    }
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
                val optJarFile = File(destJar.parent, destJar.name + ".opt")
                if (optJarFile.exists()) {
                    optJarFile.delete()
                }
                val jarOutputStream = JarOutputStream(FileOutputStream(optJarFile))

                jarFile.entries().iterator().forEach { jarEntry: JarEntry ->
                    val jarName = jarEntry.name
                    val zipEntry = ZipEntry(jarName)
                    val inputStream = jarFile.getInputStream(jarEntry)
                    jarOutputStream.putNextEntry(zipEntry)
                    //jarName 有时候是文件夹
                    val canonicalName = jarName.split("""\\.""")[0].replace("/", ".")
                    val fileName = jarName.substring(jarName.lastIndexOf("/") + 1)
                    if (!fileName.endsWith(".class") || (fileName == ("R.class"))
                        || fileName.startsWith("R\$")
                        || (fileName == ("BuildConfig.class"))
                    ) {
                        jarOutputStream.write(inputStream.readBytes())
                        inputStream.close()
                        jarOutputStream.closeEntry()
                        return
                    }
                    val codes = onInsertCode(destJar, inputStream, canonicalName)
                    if (codes?.isNotEmpty() == true) {
                        jarOutputStream.write(codes)
                    } else {
                        jarOutputStream.write(inputStream.readBytes())
                    }
                    inputStream.close()
                    jarOutputStream.closeEntry()

                }
                jarOutputStream.close()
                jarFile.close()
                if (destJar.exists()) {
                    destJar.delete()
                }
                optJarFile.renameTo(destJar)
            }
        }

        onInsertCodeEnd()
    }


}
package com.jamesfchen.kotlin

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jul/09/2021  Fri
 */
fun injectCode(info: ClassInfo, closure: (type: String, inputStream: InputStream) -> ByteArray) {
    if (info.classStream != null) {
        val jarFile = JarFile(info.mather)
        val optJarFile = File(info.mather.parent, info.mather.name + ".opt")
        if (optJarFile.exists()) {
            optJarFile.delete()
        }
        val jarOutputStream = JarOutputStream(FileOutputStream(optJarFile))

        jarFile.entries().iterator().forEach { jarEntry ->
            val jarName = jarEntry.name
            val zipEntry = ZipEntry(jarName)
            val inputStream = jarFile.getInputStream(jarEntry)
            jarOutputStream.putNextEntry(zipEntry)
            if (info.canonicalName.replace(".", "/") + ".class" == jarName) {
                val codes = closure("jar", inputStream)
                jarOutputStream.write(codes)
            } else {
                jarOutputStream.write(inputStream.readBytes())
            }
            inputStream.close()
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        jarFile.close()
        if (info.mather.exists()) {
            info.mather.delete()
        }
        optJarFile.renameTo(info.mather)
    } else {
        val inputStream = info.classFile?.inputStream()
        inputStream?.let {
            val codes = closure("dir", it)
            FileUtils.writeByteArrayToFile(info.classFile, codes)
        }
    }
}
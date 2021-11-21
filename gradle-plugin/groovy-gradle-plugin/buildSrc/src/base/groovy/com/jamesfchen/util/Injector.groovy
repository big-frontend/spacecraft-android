package com.jamesfchen.util

import com.jamesfchen.ClassInfo
import org.apache.commons.io.FileUtils

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class Injector {
    static void injectCode(ClassInfo info, Closure<String> closure) {
        if (info.classStream) {
            JarFile jarFile = new JarFile(info.mather)
            def optJarFile = new File(info.mather.getParent(), info.mather.name + ".opt")
            if (optJarFile.exists()) {
                optJarFile.delete()
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJarFile))

            jarFile.entries().each { jarEntry ->
                String jarName = jarEntry.name
                ZipEntry zipEntry = new ZipEntry(jarName)
                InputStream inputStream = jarFile.getInputStream(jarEntry)
                jarOutputStream.putNextEntry(zipEntry)
                if (info.canonicalName.replace('.', '/') + '.class' == jarName) {
                    byte[] codes = closure("jar",inputStream)
                    jarOutputStream.write(codes)
                } else {
                    jarOutputStream.write(inputStream.bytes)
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
            info.classFile.withInputStream {
                byte[] codes = closure("dir",it)
                FileUtils.writeByteArrayToFile(info.classFile, codes)
            }
        }
    }

}
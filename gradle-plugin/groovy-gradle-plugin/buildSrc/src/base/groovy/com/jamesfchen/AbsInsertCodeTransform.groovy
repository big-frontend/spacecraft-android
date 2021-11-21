package com.jamesfchen

import com.android.build.api.transform.*
import groovy.io.FileType
import org.apache.commons.io.FileUtils

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

abstract class AbsInsertCodeTransform extends Transform implements IInsertCode {

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        def outputProvider = transformInvocation.outputProvider
        onInsertCodeBegin()
        transformInvocation.inputs.each { TransformInput input ->
            //源代码编译之后的class目录
            for (int j = 0; j < input.directoryInputs.size(); ++j) {
                DirectoryInput directoryInput = input.directoryInputs[j]
                File srcDir = directoryInput.file

                File destDir = outputProvider.getContentLocation(
                        directoryInput.getName(), directoryInput.getContentTypes(),
                        directoryInput.getScopes(), Format.DIRECTORY
                )
                String rootPath = destDir.absolutePath
                if (!rootPath.endsWith(File.separator)) {
                    rootPath += File.separator
                }
                FileUtils.copyDirectory(srcDir, destDir)

                destDir.eachFileRecurse(FileType.FILES) {
                    if (!it.name.endsWith(".class") || (it.name == ("R.class"))
                            || it.name.startsWith("R\$") || (it.name == ("BuildConfig.class"))) {
                        return
                    }
                    def classPath = it.absolutePath.replace(rootPath, '')
                    def canonicalName = classPath.split('\\.')[0].replace(File.separator, '.')
                    it.withInputStream { fis ->
                        byte[] codes = onInsertCode(destDir, fis, canonicalName)

                        if (codes != null && codes.length > 0) {
                            it.withOutputStream { os ->
                                os.write(codes)
                            }
                        }
                    }
                }

            }
            //jar包，jar包或者aar包
            for (int k = 0; k < input.jarInputs.size(); ++k) {
                JarInput jarInput = input.jarInputs[k]
                File destJar = outputProvider.getContentLocation(
                        jarInput.getName(), jarInput.getContentTypes(),
                        jarInput.getScopes(), Format.JAR
                )
                FileUtils.copyFile(jarInput.file, destJar)

                JarFile jarFile = new JarFile(destJar)
                def optJarFile = new File(destJar.getParent(), destJar.name + ".opt")
                if (optJarFile.exists()) {
                    optJarFile.delete()
                }
                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJarFile))

                jarFile.entries().each { jarEntry ->
                    String jarName = jarEntry.name
                    ZipEntry zipEntry = new ZipEntry(jarName)
                    InputStream inputStream = jarFile.getInputStream(jarEntry)
                    jarOutputStream.putNextEntry(zipEntry)
                    //jarName 有时候是文件夹
                    String canonicalName = jarName.split('\\.')[0].replace('/', '.')
                    String fileName = jarName.substring(jarName.lastIndexOf('/') + 1)
                    if (!fileName.endsWith(".class") || (fileName == ("R.class"))
                            || fileName.startsWith("R\$")
                            || (fileName == ("BuildConfig.class"))) {
                        jarOutputStream.write(inputStream.bytes)
                        inputStream.close()
                        jarOutputStream.closeEntry()
                        return
                    }
                    byte[] codes = onInsertCode(destJar, inputStream, canonicalName)
                    if (codes != null && codes.size() > 0) {
                        jarOutputStream.write(codes)
                    } else {
                        jarOutputStream.write(inputStream.bytes)
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
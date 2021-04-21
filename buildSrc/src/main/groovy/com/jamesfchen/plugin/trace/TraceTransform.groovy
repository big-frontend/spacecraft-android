package com.jamesfchen.plugin.trace

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.jamesfchen.plugin.util.P
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class TraceTransform extends Transform {

    Project project

    TraceTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "TraceLog"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        P.info("TraceTransform transform start\n"+transformInvocation.inputs.size()+" "+transformInvocation.inputs+"\n"+transformInvocation.referencedInputs)
        def i=0
        transformInvocation.inputs.each { TransformInput input ->
            P.info(i+" input:jarInputs size:"+input.jarInputs.size() +"\tdirectoryInputs size:"+input.directoryInputs.size())
            def j=0
            input.directoryInputs.each { DirectoryInput directoryInput ->
                P.info(i+"\t"+j+" directoryInput:"+directoryInput.file)
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class") && !(name == ("R.class"))
                                && !name.startsWith("R\$") && !(name == ("BuildConfig.class"))) {

                            ClassReader reader = new ClassReader(file.bytes)
                            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor visitor = new TraceClassVisitor(writer)
                            reader.accept(visitor, ClassReader.EXPAND_FRAMES)

                            byte[] code = writer.toByteArray()
                            def classPath = file.parentFile.absolutePath + File.separator + name
                            P.info("instrumentation:"+classPath)
                            FileOutputStream fos = new FileOutputStream(classPath)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)


                FileUtils.copyDirectory(directoryInput.file, dest)
                j++
            }
            def k=0
            input.jarInputs.each { JarInput jarInput ->
                P.info(i+"\t"+k+" jarInput:"+jarInput.name)
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)
                k++
            }
            ++i
        }
    }
}
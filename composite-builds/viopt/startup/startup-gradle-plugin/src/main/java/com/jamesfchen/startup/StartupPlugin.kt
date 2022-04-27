package com.jamesfchen.startup

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import com.jamesfchen.ClassInfo
import com.jamesfchen.P
import com.jamesfchen.ScanClassPlugin
import org.objectweb.asm.*
import java.io.File
import java.io.FileOutputStream

const val DELEGATE_CLASS_PATH = "com/jamesfchen/startup/generated/AppDelegate";
const val DELEGATE_ANNOTATION_DESCRIPTOR = "Lcom/jamesfchen/startup/Delegate;"
const val PKG = "com.jamesfchen.startup.generated"

class StartupPlugin : ScanClassPlugin() {
    override fun getName() = "Startup"
    lateinit var delegateClassFile: File
    var appClassInfo: ClassInfo? = null
    override fun onScanBegin(transformInvocation: TransformInvocation) {
        val dest = transformInvocation.outputProvider.getContentLocation(
            "Startup", TransformManager.CONTENT_CLASS,
            ImmutableSet.of(QualifiedContent.Scope.PROJECT), Format.DIRECTORY
        )
        delegateClassFile = File(dest, DELEGATE_CLASS_PATH + SdkConstants.DOT_CLASS)
    }

    override fun onScanClass(classInfo: ClassInfo) {
//        if (classInfo.canonicalName.startsWith(PKG))
        //初次构建，查找delegate的类，为后面插入字节码做准备
        if (!delegateClassFile.exists() && appClassInfo == null) {
            P.debug("${classInfo.status}\t${classInfo.canonicalName}\t${classInfo.mather}")

            val reader = when (classInfo.status) {
                ClassInfo.BIRTH_DIR, ClassInfo.DEATH_DIR -> {
                    ClassReader(classInfo.classFile.readBytes())
                }
                ClassInfo.BIRTH_JAR, ClassInfo.DEATH_JAR -> {
                    ClassReader(classInfo.classStream)
                }
                else -> {
                    throw  UnsupportedOperationException("不支持 " + classInfo.status)
                }
            }
            val writer = ClassWriter(reader, 0)
            val visitor = when (classInfo.status) {
                ClassInfo.BIRTH_DIR, ClassInfo.BIRTH_JAR -> {
                    ScanClassVisitor(writer, classInfo)
                }
                ClassInfo.DEATH_DIR, ClassInfo.DEATH_JAR -> {
                    ScanClassVisitor(writer, classInfo)
                }
                else -> {
                    throw UnsupportedOperationException("不支持 " + classInfo.status)
                }
            }
            reader.accept(visitor, ClassReader.SKIP_FRAMES)
        }


    }

    override fun onScanEnd() {
        if (delegateClassFile.exists()) {
            P.warn("AppDelegate:$delegateClassFile")
            //插桩AppDelegate
        } else {
            P.warn("AppDelegate not exists")
            //生成 AppDelegate类
            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS);
            val cv = object : ClassVisitor(Opcodes.ASM5, writer) {};
            cv.visit(50, Opcodes.ACC_PUBLIC, DELEGATE_CLASS_PATH, null, "java/lang/Object", null);

            var mv = cv.visitMethod(
                Opcodes.ACC_PUBLIC,
                "attachBaseContext",
                "(Landroid/content/Context;)V",
                null,
                null
            );

            mv.visitCode()

            mv.visitMaxs(0, 0)
            mv.visitInsn(Opcodes.RETURN)
            mv.visitEnd()

            mv = cv.visitMethod(
                Opcodes.ACC_PUBLIC,
                "onCreate",
                "()V",
                null,
                null
            );

            mv.visitCode()

            mv.visitMaxs(0, 0)
            mv.visitInsn(Opcodes.RETURN)
            mv.visitEnd()

            cv.visitEnd()

            delegateClassFile.parentFile.mkdirs();
            FileOutputStream(delegateClassFile).use {
                it.write(writer.toByteArray())
            }

        }
    }

    inner class ScanClassVisitor(
        classWriter: ClassWriter,
        val classInfo: ClassInfo
    ) : ClassVisitor(Opcodes.ASM6, classWriter) {
        var hasDelegateAnnotation = false
        override fun visit(
            version: Int,
            access: Int,
            name: String,
            signature: String,
            superName: String,
            interfaces: Array<String>
        ) {
            super.visit(version, access, name, signature, superName, interfaces)
        }

        override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitor {
            if (DELEGATE_ANNOTATION_DESCRIPTOR == descriptor) {
                hasDelegateAnnotation = true
            }
            return super.visitAnnotation(descriptor, visible)
        }

        @Override
        override fun visitEnd() {
            super.visitEnd()
            if (hasDelegateAnnotation) {
                appClassInfo = classInfo
            }
            hasDelegateAnnotation = false

        }
    }
}
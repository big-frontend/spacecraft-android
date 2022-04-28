package com.jamesfchen.startup

import com.android.SdkConstants
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import com.jamesfchen.ClassInfo
import com.jamesfchen.Injector
import com.jamesfchen.P
import com.jamesfchen.ScanClassPlugin
import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter
import java.io.File
import java.io.FileOutputStream


const val DELEGATE_CLASS_PATH = "com/jamesfchen/startup/generated/AppDelegate";
const val DELEGATE_ANNOTATION_DESCRIPTOR = "Lcom/jamesfchen/startup/Delegate;"
const val PKG = "com.jamesfchen.startup.generated"

class StartupPlugin : ScanClassPlugin() {
    override fun getName() = "Startup"
    lateinit var appDelegateClassFile: File
    var appClassInfo: ClassInfo? = null

    override fun onScanBegin(transformInvocation: TransformInvocation) {
        val dest = transformInvocation.outputProvider.getContentLocation(
            "Startup", TransformManager.CONTENT_CLASS,
            ImmutableSet.of(QualifiedContent.Scope.PROJECT), Format.DIRECTORY
        )
        appDelegateClassFile = File(dest, DELEGATE_CLASS_PATH + SdkConstants.DOT_CLASS)
    }

    override fun onScanClass(classInfo: ClassInfo) {
    }

    override fun onScanEnd() {
        P.info("startup: ${appClassInfo}")
        if (appClassInfo == null) return
        if (appDelegateClassFile.exists()) {
            P.warn("AppDelegate:$appDelegateClassFile")
//            instrumentationAppDelegateClass(appClassInfo!!)
        } else {
            P.warn("AppDelegate not exists")
//            genAppDelegateClass()
//            instrumentationAppDelegateClass(appClassInfo!!)
        }
    }

    private fun genAppDelegateClass() {
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

        cv.visitField(Opcodes.ACC_PRIVATE, "delegate", DELEGATE_CLASS_PATH, null, null).visitEnd()

        var av0: AnnotationVisitor
        mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null)
        mv.visitCode()
        val l0: Label = Label()
        mv.visitLabel(l0)
        mv.visitLineNumber(3, l0)
        mv.visitVarInsn(Opcodes.ALOAD, 0)
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        val l1: Label = Label()
        mv.visitLabel(l1)
        mv.visitLineNumber(4, l1)
        mv.visitVarInsn(Opcodes.ALOAD, 0)
        mv.visitTypeInsn(Opcodes.NEW, "com/jamesfchen/manager/AppDelegate")
        mv.visitInsn(Opcodes.DUP)
        mv.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "com/jamesfchen/manager/AppDelegate",
            "<init>",
            "()V",
            false
        )
        mv.visitFieldInsn(
            Opcodes.PUTFIELD,
            "com/jamesfchen/manager/AppDelegate",
            "delegate",
            "Lcom/jamesfchen/manager/AppDelegate;"
        )
        mv.visitInsn(Opcodes.RETURN)
        val l2 = Label()
        mv.visitLabel(l2)
        mv.visitLocalVariable("this", "Lcom/jamesfchen/manager/AppDelegate;", null, l0, l2, 0)
        mv.visitMaxs(3, 1)
        mv.visitEnd()

        cv.visitEnd()

        appDelegateClassFile.parentFile.mkdirs();
        FileOutputStream(appDelegateClassFile).use {
            it.write(writer.toByteArray())
        }
    }

    private fun instrumentationAppDelegateClass(classInfo: ClassInfo) {
        Injector.injectCode(classInfo, Injector.Callback l@{ where, classStream ->
            val reader = ClassReader(classStream)
            val writer = ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
            val visitor = object : ClassVisitor(Opcodes.ASM5, writer) {
                override fun visitField(
                    access: Int,
                    name: String?,
                    descriptor: String?,
                    signature: String?,
                    value: Any?
                ): FieldVisitor {
                    return super.visitField(access, name, descriptor, signature, value)
                }

                override fun visitMethod(
                    access: Int,
                    name: String?,
                    descriptor: String?,
                    signature: String?,
                    exceptions: Array<out String>?
                ): MethodVisitor {
                    val methodVisitor =
                        super.visitMethod(access, name, descriptor, signature, exceptions)
                    if ("attachBaseContext" == name) {
                        return object :
                            AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, descriptor) {
                            override fun onMethodExit(opcode: Int) {
                                super.onMethodExit(opcode)
                                //appdelegate.attachBaseContext
                                mv.visitMethodInsn(
                                    Opcodes.INVOKEVIRTUAL,
                                    DELEGATE_CLASS_PATH,
                                    "attachBaseContext",
                                    "(Landroid/content/Context;)V",
                                    false
                                )
                            }
                        }
                    } else if ("onCreate" == name) {
                        return object :
                            AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, descriptor) {
                            override fun onMethodExit(opcode: Int) {
                                super.onMethodExit(opcode)
                                //appdelegate.onCreate
                                mv.visitMethodInsn(
                                    Opcodes.INVOKEVIRTUAL,
                                    DELEGATE_CLASS_PATH,
                                    "onCreate",
                                    "()V",
                                    false
                                )
                            }
                        }
                    }
                    return methodVisitor

                }
            }
            reader.accept(visitor, ClassReader.SKIP_DEBUG)
            return@l writer.toByteArray()
        })
    }

}
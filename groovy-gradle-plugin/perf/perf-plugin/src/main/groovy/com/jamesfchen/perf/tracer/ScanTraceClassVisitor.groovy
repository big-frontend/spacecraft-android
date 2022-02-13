package com.jamesfchen.perf.tracer

import com.jamesfchen.ClassInfo
import org.objectweb.asm.*
import org.objectweb.asm.commons.AdviceAdapter

class ScanTraceClassVisitor extends ClassVisitor {
    private static final String TRACE_ANNOTATION_DESC = "Lcom/jamesfchen/annotations/TraceTime;"
    boolean hasTrace = false
    List<ClassInfo> traceClassInfos
    ClassInfo classFile

    ScanTraceClassVisitor(ClassWriter classVisitor, List<ClassInfo> traceClassInfos, ClassInfo classFile) {
        super(Opcodes.ASM6, classVisitor)
        this.traceClassInfos = traceClassInfos
        this.classFile = classFile
    }
    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        methodVisitor = new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor) {
            @Override
            AnnotationVisitor visitAnnotation(String ds, boolean visible) {
                if (TRACE_ANNOTATION_DESC == ds) {
                    hasTrace = true
                }
                return super.visitAnnotation(ds, visible)
            }

        }
        return methodVisitor
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (hasTrace) {
            if (traceClassInfos != null) {
                traceClassInfos.add(classFile)
            }
        }
    }
}
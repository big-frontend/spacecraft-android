package com.jamesfchen.plugin.trace

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TraceClassVisitor extends ClassVisitor {

    private String mClassName

    TraceClassVisitor(int api) {
        super(api)
    }

    TraceClassVisitor(int api, ClassVisitor cv) {
        super(api, cv)
    }

    TraceClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor)
    }
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.mClassName = name
    }


    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        methodVisitor = new TraceAdviceAdapter(Opcodes.ASM5, methodVisitor, access, mClassName, name, desc)
        return methodVisitor
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor fieldVisitor =  super.visitField(access, name, desc, signature, value)
//        fieldVisitor = new TraceFiledVisitor(Opcodes.ASM5,fieldVisitor,)
        return fieldVisitor
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }

}
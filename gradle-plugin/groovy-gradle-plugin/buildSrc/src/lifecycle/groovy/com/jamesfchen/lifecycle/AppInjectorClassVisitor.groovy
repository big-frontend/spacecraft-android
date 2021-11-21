package com.jamesfchen.lifecycle

import com.jamesfchen.util.P
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class AppInjectorClassVisitor extends ClassVisitor{
    List<String> lifecycles
    private hasOnCreateMethod=false
    AppInjectorClassVisitor(ClassVisitor classVisitor, List<String> lifecycles) {
        super(Opcodes.ASM6, classVisitor)
        this.lifecycles = lifecycles
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        P.info("lifecycle 将要注入代码到${name}")
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if ("onCreate" != name )return methodVisitor
        if (lifecycles == null || lifecycles.isEmpty()) {
            P.info("lifecycles数据为空")
            return methodVisitor
        }
        hasOnCreateMethod = true
        methodVisitor = new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, descriptor){
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter()
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode)
                lifecycles.each {
//                    def clz = it.replace('/','.')
//                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitLdcInsn(Type.getType("L" + it + ";"));
                    mv.visitMethodInsn(INVOKESTATIC, "com/jamesfchen/lifecycle/Injector", "injectApp", "(Ljava/lang/Class;)V", false);
                }

            }
        }
        return methodVisitor
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (!hasOnCreateMethod){
            P.info("没有找到onCreate方法，app类需要重写onCreate")
        }
    }
}
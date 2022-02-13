package com.jamesfchen.perf.tracer

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.Opcodes
import com.jamesfchen.P
class TraceAdviceAdapter extends AdviceAdapter {

    private static final String COST_ANNOTATION_DESC = "Lcom/jamesfchen/annotations/TraceTime;"

    private boolean isInjected = false

    private int startTimeId
    private String className

    private String methodName

    private String desc

    private boolean isStaticMethod

    private Type[] argumentArrays

    TraceAdviceAdapter(int api, MethodVisitor mv, int access, String className, String methodName, String desc) {
        super(api, mv, access, methodName, desc)
        this.className = className
        this.methodName = methodName
        this.desc = desc
        argumentArrays = Type.getArgumentTypes(desc)
        isStaticMethod = ((access & ACC_STATIC) != 0)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (COST_ANNOTATION_DESC == desc) {
            isInjected = true
        }
        return super.visitAnnotation(desc, visible)
    }


    @Override
    protected void onMethodEnter() {
        if (!isInjected) return
        P.info("将要注入代码到 ${className}#${methodName}")
        startTimeId = newLocal(Type.LONG_TYPE)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitVarInsn(LSTORE, startTimeId)
//
//        for (int i = 0; i < argumentArrays.length; i++) {
//            Type type = argumentArrays[i]
//            int index = isStaticMethod ? i : (i + 1)
//            switch (type.getSort()) {
//                case Type.BOOLEAN:
//                case Type.CHAR:
//                case Type.BYTE:
//                case Type.SHORT:
//                case Type.INT:
//                    mv.visitVarInsn(ILOAD, index)
//                    box(type)
//                    break
//                case Type.FLOAT:
//                    mv.visitVarInsn(FLOAD, index)
//                    box(type)
//                    break
//                case Type.LONG:
//                    mv.visitVarInsn(LLOAD, index)
//                    box(type)
//                    break
//                case Type.DOUBLE:
//                    mv.visitVarInsn(DLOAD, index)
//                    box(type)
//                    break
//                case Type.ARRAY:
//                case Type.OBJECT:
//                    mv.visitVarInsn(ALOAD, index)
//                    box(type)
//                    break
//            }
//            mv.visitVarInsn(ILOAD, methodId)
//            visitMethodInsn(INVOKESTATIC, "com/example/tracelibrary/core/MethodCache", "addMethodArgument",
//                    "(Ljava/lang/Object;I)V", false)
//        }


    }

    @Override
    protected void onMethodExit(int opcode) {
        if (!isInjected) return
        P.info("onMethodExit:" + opcode)
//        if (opcode == RETURN) {
//            visitInsn(ACONST_NULL)
//        } else if (opcode == ARETURN || opcode == ATHROW) {
//            dup()
//        } else {
//            if (opcode == LRETURN || opcode == DRETURN) {
//                dup2()
//            } else {
//                dup()
//            }
//            box(Type.getReturnType(this.methodDesc))
//        }
//        Log.d("cjf", "耗时:" + (System.currentTimeMillis() - start));
        mv.visitLdcInsn("cjf")
        mv.visitTypeInsn(NEW, 'java/lang/StringBuilder')
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, 'java/lang/StringBuilder', "<init>", "()V", false);
        mv.visitLdcInsn(className+" ["+methodName+"] 耗时:")
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
//        int endTimeId = newLocal(Type.LONG_TYPE)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
//        mv.visitVarInsn(LSTORE, endTimeId)
//        mv.visitVarInsn(LLOAD, endTimeId)
        mv.visitVarInsn(LLOAD, startTimeId)
        mv.visitInsn(LSUB)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false)
    }

    void printSomething(String v) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        mv.visitLdcInsn(v)
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
    }

    private static void createIntegerObj(MethodVisitor mv, int argsPostion) {
        //
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/Integer");
        mv.visitInsn(Opcodes.DUP);
        mv.visitVarInsn(Opcodes.ILOAD, argsPostion);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V", false);
        mv.visitInsn(Opcodes.AASTORE);
    }

    private static void createCharObj(MethodVisitor mv, int argsPostion) {
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/Character");
        mv.visitInsn(Opcodes.DUP);
        mv.visitVarInsn(Opcodes.ILOAD, argsPostion);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Character", "<init>", "(C)V");
        mv.visitInsn(Opcodes.AASTORE);
    }

}

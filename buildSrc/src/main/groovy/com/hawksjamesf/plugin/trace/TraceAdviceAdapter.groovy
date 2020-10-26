package com.hawksjamesf.plugin.trace

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import com.hawksjamesf.plugin.util.P
import org.objectweb.asm.Opcodes

class TraceAdviceAdapter extends AdviceAdapter {

    private static final String COST_ANNOTATION_DESC = "Lcom/hawksjamesf/annotations/TraceTime;"

    private boolean isInjected = false

    private int startTimeId

    private int methodId

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
        isStaticMethod = ((access & Opcodes.ACC_STATIC) != 0)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        P.info("TraceAdviceAdapter:" + desc)
        if (COST_ANNOTATION_DESC == desc) {
            isInjected = true
        }
        return super.visitAnnotation(desc, visible)
    }


    @Override
    protected void onMethodEnter() {
        if (!isInjected) return
        startTimeId = newLocal(Type.LONG_TYPE)
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitIntInsn(LSTORE, startTimeId)

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
        mv.visitLdcInsn("耗时:")
//        mv.visitVarInsn(LLOAD,)
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)V", false)
//        mv.visitLdcInsn(className)
//        mv.visitLdcInsn(methodName)
//        mv.visitLdcInsn(desc)
//        mv.visitVarInsn(LLOAD, startTimeId)
//        mv.visitVarInsn(ILOAD, methodId)
//        mv.visitMethodInsn(INVOKESTATIC, "com/example/tracelibrary/core/MethodCache", "updateMethodInfo",
//                "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JI)V", false)
//
//        mv.visitVarInsn(ILOAD, methodId)
//        mv.visitMethodInsn(INVOKESTATIC, "com/example/tracelibrary/core/MethodCache",
//                "printMethodInfo", "(I)V", false)
    }
}

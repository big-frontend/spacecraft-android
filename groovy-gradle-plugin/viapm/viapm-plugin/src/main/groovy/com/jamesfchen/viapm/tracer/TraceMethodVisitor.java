package com.jamesfchen.viapm.tracer;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * author jamesfchen
 * since Oct/26/2020  Mon
 */
public class TraceMethodVisitor extends MethodVisitor {
    public TraceMethodVisitor(int api) {
        super(api);
    }

    public TraceMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitInsn(int opcode) {
        //在return之前插入代码
        if ((Opcodes.IRETURN<=opcode && opcode <= Opcodes.RETURN)) {

        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}

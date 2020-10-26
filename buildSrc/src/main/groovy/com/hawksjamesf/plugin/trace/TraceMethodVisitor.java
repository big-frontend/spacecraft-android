package com.hawksjamesf.plugin.trace;

import org.objectweb.asm.MethodVisitor;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Oct/26/2020  Mon
 */
public class TraceMethodVisitor extends MethodVisitor {
    public TraceMethodVisitor(int api) {
        super(api);
    }

    public TraceMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}

package com.jamesfchen.perf.tracer;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * author jamesfchen
 * since Oct/26/2020  Mon
 */
public class TraceFiledVisitor extends FieldVisitor {
    public TraceFiledVisitor(int api) {
        super(api);
    }

    public TraceFiledVisitor(int api, FieldVisitor fv) {
        super(api, fv);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        super.visitAttribute(attr);
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}

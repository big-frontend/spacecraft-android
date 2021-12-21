package com.jamesfchen.ibc

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class ScanRouterClassVisitor extends ClassVisitor {
    private static final String ROUTER_ANNOTATION_DESC = "Lcom/jamesfchen/ibc/Router;"
    boolean hasRouterAnnotation = false
    String canonicalName
    String routerName
    List<RouterInfo> routers

    ScanRouterClassVisitor(ClassWriter classVisitor, List<RouterInfo> routers) {
        super(Opcodes.ASM6, classVisitor)
        this.routers = routers
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        canonicalName = name
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (ROUTER_ANNOTATION_DESC == descriptor) {
            hasRouterAnnotation = true
        }
        AnnotationVisitor av = super.visitAnnotation(descriptor, visible)
        av = new AnnotationVisitor(Opcodes.ASM5,av) {
            @Override
            void visit(String name, Object value) {
                super.visit(name, value)
                if (hasRouterAnnotation){
//                    P.info("cjf "+name + " = " + value)
                    routerName = value
                }
            }

        }
        return av
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (hasRouterAnnotation) {
            if (routers != null) {
                routers.add(new RouterInfo(routerName,canonicalName))
            }
        }
    }
}
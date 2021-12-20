package com.jamesfchen.lifecycle

import com.jamesfchen.ClassInfo
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class ScanLifecycleClassVisitor extends ClassVisitor{
    private static final String APP_LIFECYCLE_ANNOTATION_DESC = "Lcom/jamesfchen/lifecycle/AppLifecycle;"
    private static final String APP_ANNOTATION_DESC = "Lcom/jamesfchen/lifecycle/App;"
    boolean hasAppAnnotation=false
    boolean hasAppLifecycleAnnotation=false
    String canonicalName
    List<String> lifecycles
    List<ClassInfo> appClassInfos
    ClassInfo classFile

    ScanLifecycleClassVisitor(ClassWriter classVisitor, List<String> lifecycles, List<ClassInfo> appClassInfos,ClassInfo classFile) {
        super(Opcodes.ASM6, classVisitor)
        this.lifecycles = lifecycles
        this.appClassInfos = appClassInfos
        this.classFile = classFile
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        canonicalName = name
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (APP_LIFECYCLE_ANNOTATION_DESC == descriptor){
            hasAppLifecycleAnnotation = true
        }else if (APP_ANNOTATION_DESC == descriptor){
            hasAppAnnotation = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        if (hasAppLifecycleAnnotation){
            if (lifecycles !=null){
                lifecycles.add(canonicalName)
            }
        }else if (hasAppAnnotation){
            if (appClassInfos !=null){
                appClassInfos.add(classFile)
            }
        }
        hasAppLifecycleAnnotation = false
        hasAppAnnotation = false

    }
}
package com.jamesfchen.ibc

import com.jamesfchen.ClassInfo
import com.jamesfchen.ScanClassPlugin
import com.jamesfchen.util.Injector
import com.jamesfchen.util.P
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class IbcPlugin extends ScanClassPlugin {
    List<RouterInfo> routers
    ClassInfo ibcRouterClassInfo

    @Override
    String pluginName() {
        return "Ibc"
    }
    @Override
    void apply(Project project) {
        super.apply(project)
        P.info("project[${project}] apply ${this.getClass().getSimpleName()}")
    }
    @Override
    void onScanBegin() {
        routers = new ArrayList<>()
    }

    @Override
    void onScanClassInDir(ClassInfo classInfo) {
        if (classInfo.canonicalName == "com.jamesfchen.ibc.route.IBCRouter") {
            ibcRouterClassInfo = classInfo
            return
        }
        ClassReader reader = new ClassReader(classInfo.classFile.bytes)
        ClassWriter writer = new ClassWriter(reader, 0)
        ClassVisitor visitor = new ScanRouterClassVisitor(writer, routers)
        reader.accept(visitor, ClassReader.SKIP_FRAMES)
    }

    @Override
    void onScanClassInJar(ClassInfo classInfo) {
        if (classInfo.canonicalName == "com.jamesfchen.ibc.route.IBCRouter") {
            ibcRouterClassInfo = classInfo
            return
        }
        ClassReader reader = new ClassReader(classInfo.classStream)
        ClassWriter writer = new ClassWriter(reader, 0)
        ClassVisitor visitor = new ScanRouterClassVisitor(writer, routers)
        reader.accept(visitor, ClassReader.SKIP_FRAMES)
    }

    @Override
    void onScanEnd() {
        P.info("routers:${routers.toListString()} IBCRouter:${ibcRouterClassInfo}")
        if (routers.isEmpty() ||ibcRouterClassInfo ==null) return
        Injector.injectCode(ibcRouterClassInfo) { type, classStream ->
            ClassReader reader = new ClassReader(classStream)
            ClassWriter writer = new ClassWriter(reader, 0)
            ClassVisitor visitor = new RouterInjectorClassVisitor(writer, routers)
            reader.accept(visitor, ClassReader.SKIP_FRAMES)
            return writer.toByteArray()
        }
    }
}
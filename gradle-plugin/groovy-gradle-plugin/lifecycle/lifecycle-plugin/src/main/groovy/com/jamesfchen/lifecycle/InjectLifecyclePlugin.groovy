package com.jamesfchen.lifecycle

import com.jamesfchen.ClassInfo
import com.jamesfchen.ScanClassPlugin
import com.jamesfchen.util.Injector
import com.jamesfchen.util.P
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class InjectLifecyclePlugin extends ScanClassPlugin{
    List<String> lifecycles
    List<ClassInfo> appClassInfos
    @Override
    String pluginName() {
        return "InjectLifecycle"
    }
    @Override
    void apply(Project project) {
        super.apply(project)
        P.info("project[${project}] apply ${this.getClass().getSimpleName()}")
    }
    @Override
    void onScanBegin() {
        lifecycles= new ArrayList<>()
        appClassInfos = new ArrayList<>()
    }

    @Override
    void onScanClassInDir(ClassInfo classInfo) {
//            P.info("cjf onInsertCodeInDir ${canonicalName}")
        ClassReader reader = new ClassReader(classInfo.classFile.bytes)
//      ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        ClassWriter writer = new ClassWriter(reader, 0)
        ClassVisitor visitor = new ScanLifecycleClassVisitor(writer,lifecycles,appClassInfos,classInfo)
        reader.accept(visitor, ClassReader.SKIP_FRAMES)
    }

    @Override
    void onScanClassInJar(ClassInfo classInfo) {
//            P.info("cjf onScanClassInJar ${classInfo.canonicalName}")
        ClassReader reader = new ClassReader(classInfo.classStream)
//      ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        ClassWriter writer = new ClassWriter(reader, 0)
        ClassVisitor visitor = new ScanLifecycleClassVisitor(writer,lifecycles,appClassInfos,classInfo)
        reader.accept(visitor, ClassReader.SKIP_FRAMES)
    }

    @Override
    void onScanEnd() {
        P.info("appFiles:${appClassInfos.toListString()}  \nobserver:${lifecycles.toListString()}")
        if (appClassInfos.isEmpty() || lifecycles.isEmpty()) return
        appClassInfos.each { classInfo->
            Injector.injectCode(classInfo){type,classStream->
                ClassReader reader = new ClassReader(classStream)
                ClassWriter writer = new ClassWriter(reader, 0)
                ClassVisitor visitor = new AppInjectorClassVisitor(writer, lifecycles)
                reader.accept(visitor, ClassReader.SKIP_FRAMES)
                return writer.toByteArray()
            }
        }
    }
}
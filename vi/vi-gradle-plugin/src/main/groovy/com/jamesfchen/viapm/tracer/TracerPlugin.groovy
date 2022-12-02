package com.jamesfchen.viapm.tracer

import org.gradle.api.Plugin
import org.gradle.api.Project
class TracerPlugin implements  Plugin<Project>  {
//    @Override
//    protected String getName() {
//        return 'Tracer'
//    }
    @Override
    void apply(Project project) {
        super.apply(project)
        def perfExtension = project.extensions.create('viapm', TracerExtension)
    }
//    @Override
//    void onInsertCodeBegin() {
//
//    }
//
//    @Override
//    byte[] onInsertCode(File mather, InputStream classStream, String canonicalName) {
//        ClassReader reader = new ClassReader(classStream)
//        ClassWriter writer = new ClassWriter(reader, 0)
//        ClassVisitor visitor = new TraceClassVisitor(writer)
//        reader.accept(visitor, ClassReader.SKIP_FRAMES)
//        return writer.toByteArray()
//    }
//
//    @Override
//    void onInsertCodeEnd() {
//
//    }
}

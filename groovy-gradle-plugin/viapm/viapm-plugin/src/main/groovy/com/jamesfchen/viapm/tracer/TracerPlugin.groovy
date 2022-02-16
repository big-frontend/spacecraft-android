package com.jamesfchen.viapm.tracer

import com.jamesfchen.FastInsertCodePlugin
import com.jamesfchen.P
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class TracerPlugin extends FastInsertCodePlugin  {
    @Override
    protected String pluginName() {
        return 'Tracer'
    }
    @Override
    void apply(Project project) {
        super.apply(project)
        P.info("project[${project}] apply ${this.getClass().getSimpleName()}")
        def perfExtension = project.extensions.create('viapm', TracerExtension)
    }
    @Override
    void onInsertCodeBegin() {

    }

    @Override
    byte[] onInsertCode(File mather, InputStream classStream, String canonicalName) {
        ClassReader reader = new ClassReader(classStream)
        ClassWriter writer = new ClassWriter(reader, 0)
        ClassVisitor visitor = new TraceClassVisitor(writer)
        reader.accept(visitor, ClassReader.SKIP_FRAMES)
        return writer.toByteArray()
    }

    @Override
    void onInsertCodeEnd() {

    }
}

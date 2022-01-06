package com.jamesfchen.perf


import com.jamesfchen.FastInsertCodePlugin
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.jamesfchen.P
import com.jamesfchen.F
import org.gradle.api.Project
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class PerfPlugin extends FastInsertCodePlugin  {
    @Override
    protected String pluginName() {
        return 'Performance'
    }
    @Override
    void apply(Project project) {
        super.apply(project)
        P.info("project[${project}] apply ${this.getClass().getSimpleName()}")
        def perfExtension = project.extensions.create('perf', PerfExtension)
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

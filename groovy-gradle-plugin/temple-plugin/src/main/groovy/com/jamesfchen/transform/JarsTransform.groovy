package com.jamesfchen.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.jamesfchen.P
import com.jamesfchen.F
import org.gradle.api.Project
class JarsTransform extends Transform {
    Project project

    JarsTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return JarsTransform.class.getSimpleName()
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_JARS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        P.info("JarsTransform 消费型输入源数量:" + transformInvocation.inputs.size() + "\t引用型输入源数量：" + transformInvocation.referencedInputs.size())
        def outputProvider = transformInvocation.outputProvider
        int i = 0
        transformInvocation.inputs.each { TransformInput input ->
            P.info("第" + i + "个消费型输入源\t" + " 所有的第三方包的数量:" + input.jarInputs.size() + "\t所有的class文件的数量:" + input.directoryInputs.size())
            //源代码编译之后的class目录
            for (int j = 0; j < input.directoryInputs.size(); ++j) {
                DirectoryInput directoryInput = input.directoryInputs[j]
                P.info(i + "\t" + j + " directoryInput:" + directoryInput.file)
                TransformUtil.toNextTransform(directoryInput,outputProvider)
            }
            //第三方包，jar包或者aar包
            for (int k = 0; k < input.jarInputs.size(); ++k) {
                JarInput jarInput = input.jarInputs[k]
                P.info(i + "\t" + k + " 第三方包:" + jarInput.name)
                def dest = TransformUtil.toNextTransform(jarInput,outputProvider)

            }
            ++i
        }
    }
}

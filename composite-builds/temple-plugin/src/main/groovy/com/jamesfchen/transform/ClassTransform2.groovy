package com.jamesfchen.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.jamesfchen.P
import com.jamesfchen.F
import org.gradle.api.Project
class ClassTransform2 extends Transform {
    Project project

    ClassTransform2(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return ClassTransform2.class.getSimpleName()
    }

    /***
     *         TransformManager.CONTENT_DEX
     *         TransformManager.CONTENT_DEX_WITH_RESOURCES
     *         TransformManager.CONTENT_JARS
     *         TransformManager.CONTENT_NATIVE_LIBS
     *         TransformManager.CONTENT_RESOURCES
     *         TransformManager.CONTENT_CLASS
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /***
     * -------android bundle的feature start
     * TransformManager.SCOPE_FEATURES
     * TransformManager.SCOPE_FULL_WITH_FEATURES
     * TransformManager.SCOPE_FULL_WITH_IR_AND_FEATURES
     * -------android bundle的feature end
     *
     * TransformManager.SCOPE_FULL_PROJECT
     * TransformManager.SCOPE_FULL_LIBRARY_WITH_LOCAL_JARS
     * TransformManager.SCOPE_FULL_PROJECT_WITH_LOCAL_JARS
     * TransformManager.SCOPE_FULL_WITH_IR_FOR_DEXING
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * transformInvocation.getInputs() 消费型输入源，必须传递给下一个transform
     * transformInvocation.getReferencedInputs() 引用型输入源，不用传给下一个transform
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        P.info("ClassTransform2 消费型输入源数量:" + transformInvocation.inputs.size() + "\t引用型输入源数量：" + transformInvocation.referencedInputs.size())
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
                TransformUtil.toNextTransform(jarInput,outputProvider)
            }
            ++i
        }
    }


}
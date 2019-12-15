package com.hawksjamesf.processor

import com.hawksjamesf.annotations.TraceTime
import com.hawksjamesf.annotations.TraceTime2
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement



/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/17/2019  Sat
 */
//@AutoService(Processor::class)
class TraceTimeProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf<String>(TraceTime::class.java.canonicalName, TraceTime2::class.java.canonicalName)
    }


    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        println("hawks-init")

    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        println("hawks-process")
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }


}
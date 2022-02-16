package com.jamesfchen.processor;

import com.jamesfchen.annotations.TraceTime;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * author jamesfchen
 * since Aug/17/2019  Sat
 */
//@AutoService(Processor.class)
public class TraceTimeProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<String>();
        set.add(TraceTime.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("cjf-init");
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("cjf- process");
        // 准备在gradle的控制台打印信息
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "开启日志: --------------");

        // 打印注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(TraceTime.class);
        for (Element element : elements) {
            String name = element.getSimpleName().toString();
//            String value = element.getAnnotation(TraceTime.class).value();
//            messager.printMessage(Diagnostic.Kind.NOTE, "cjf"+name + " --> " + value);
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

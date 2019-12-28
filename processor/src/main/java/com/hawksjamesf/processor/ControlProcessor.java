package com.hawksjamesf.processor;

import com.google.auto.service.AutoService;
import com.hawksjamesf.annotations.Control;
import com.hawksjamesf.annotations.TraceTime;
import com.hawksjamesf.annotations.TraceTime2;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/28/2019  Sat
 */
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//@SupportedAnnotationTypes("com.zhangjian.CustomAnnotation")
@AutoService(Processor.class)
public class ControlProcessor extends AbstractProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<String>();
        set.add(Control.class.getCanonicalName());
        set.add(TraceTime.class.getCanonicalName());
        set.add(TraceTime2.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("hawks-init ControlProcessor");
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("hawks-process ControlProcessor");
        // 准备在gradle的控制台打印信息
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "start: --------------");

        // 打印注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Control.class);
        for (Element element : elements) {
            String name = element.getSimpleName().toString();
            String value = element.getAnnotation(Control.class).value();
            messager.printMessage(Diagnostic.Kind.NOTE, "cjf"+name + " --> " + value);
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

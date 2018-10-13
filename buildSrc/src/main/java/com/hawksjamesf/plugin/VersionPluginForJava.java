package com.hawksjamesf.plugin;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.internal.reflect.Instantiator;

import util.Console;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/11/2018  Thu
 */
public class VersionPluginForJava implements Plugin<Project> {

    private Instantiator instantiator;

    public VersionPluginForJava() {
    }

    @Override
    public void apply(Project project) {
        VersionPluginExtensionForJava versionPluginForJava = project.getExtensions().create("versionPluginForJava", VersionPluginExtensionForJava.class, project);

        StartParameter startParameter = project.getGradle().getStartParameter();

        Console.printJ(startParameter.getTaskNames().toString());

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("--> version plugin for java" +
                        "\n" + versionPluginForJava.getBuildTypeMatcher() +
                        "\n" + versionPluginForJava.getFileFormat()
                        + "\n" + "<--");
//                project.android.applicationVariants.each(
//
//                );

//                 android = project.getExtensions().getByName("com.android.application");
//                AppExtension aModel = project.getExtensions().getByName("android");
//                Object obj = aModel.getApplicationVariants();
//                System.out.println(aModel.getClass().getName()+"for java");

            }
        });


        project.getTasks().create("taskForJava", TaskForJava.class, task -> {
            //configuration phase
            System.out.println("taskForJava configuration"+task);
            task.setMessage("Hello");
            task.setRecipient("World");
        });
        project.task("taskForJava2").doLast(new Action<Task>() {
            @Override
            public void execute(Task task) {
                //execution phase
                System.out.println("taskForJava2 doLast");
            }
        });

        project.getGradle().addListener(new TaskActionListenerForJava());
        project.getGradle().addListener(new TaskExecutionListenerForJava());
    }
}

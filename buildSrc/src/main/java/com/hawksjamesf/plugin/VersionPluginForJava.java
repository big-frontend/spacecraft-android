package com.hawksjamesf.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/11/2018  Thu
 */
public class VersionPluginForJava implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        VersionPluginExtensionForJava versionPluginForJava = project.getExtensions().create("versionPluginForJava", VersionPluginExtensionForJava.class,project);

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("--> version plugin for java" +
                        "\n"+versionPluginForJava.getBuildTypeMatcher()+
                        "\n"+versionPluginForJava.getFileFormat()
                +"\n"+"<--");
//                project.android.applicationVariants.each(
//
//                );

//                 android = project.getExtensions().getByName("com.android.application");
//                AppExtension aModel = project.getExtensions().getByName("android");
//                Object obj = aModel.getApplicationVariants();
//                System.out.println(aModel.getClass().getName()+"for java");
            }
        });

        project.getTasks().create("taskForJava", TaskForJava.class, (task) -> {
            task.setMessage("Hello");
            task.setRecipient("World");
        });
    }
}

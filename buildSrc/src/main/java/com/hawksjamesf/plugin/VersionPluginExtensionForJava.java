package com.hawksjamesf.plugin;

import org.gradle.api.Project;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/11/2018  Thu
 */
public class VersionPluginExtensionForJava {
    private String buildTypeMatcher;
    private String fileFormat;
    private Project project;

    public VersionPluginExtensionForJava(Project project) {
        this.project=project;
    }

    public String getBuildTypeMatcher() {
        return buildTypeMatcher;
    }

    public void setBuildTypeMatcher(String buildTypeMatcher) {
        this.buildTypeMatcher = buildTypeMatcher;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
}

package com.jamesfchen.viapm.bugly


import com.android.builder.core.DefaultManifestParser
import com.jamesfchen.P
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class ReportMappingTask extends DefaultTask {
    @Input
    Object variant

    @TaskAction
    def report() {
        P.info("BuglyPlugin mapping task action start ")
        if (project.bugly.appId == null) {
            project.logger.error("Please set your app id")
            return
        }
        if (project.bugly.appKey == null) {
            project.logger.error("Please set your app key")
            return
        }
        println "${variant.name} \n${project.bugly}"
        def version = getVersionName(variant)
        if (version == null) {
            project.logger.error("Failed to get version name of project" + project.getName())
            return
        }
        String flovorName = variant.getFlavorName()

        List<File> mappingFiles = getAllMappingFiles(project, flovorName)

        mappingFiles.each { mappingFile ->
            Report.UploadInfo uploadInfo = new Report.UploadInfo()
            uploadInfo.appId = project.bugly.appId
            uploadInfo.appKey = project.bugly.appKey
            uploadInfo.packageName = variant.applicationId
            uploadInfo.version = version
            uploadInfo.isMapping = true
            uploadInfo.file = mappingFile
//            uploadInfo.channel = "xiaomi"
            Report.uploadSymtab(uploadInfo)
        }
//        File uploadLogFile = LogFile.getLogFile(LogFile.UPLOAD_LOG_FILE_NAME,project)
//        uploadInfo.files.each { uploadFile ->
//            if (LogFile.checkLogFile(uploadLogFile, uploadFile.file)) {
//                project.logger.info("The record of symtab file is found in upload log file.")
//                return
//            }
//            Report.uploadSymtabFile(uploadInfoList)
//            if () {
//                LogFile.logFile(uploadLogFile, uploadFile.file)
//            }
//        }
        P.info("BuglyPlugin mapping task action end")
    }
    private Vector<File> getMappingFile(Project project, String flavorName) {
        if (null == project) {
            return null
        }
        if (!project.hasProperty("android")) {
            return null
        }
        Vector<File> mappingFiles = new Vector<File>()
        if (project.android.hasProperty("applicationVariants")) {
            project.android.applicationVariants.all { variant ->
                if (flavorName != null && !variant.getFlavorName().equals(flavorName)) {
                    return
                }
                if (variant.name.capitalize().contains(this.variant.name.capitalize())) {
                    File mappingFile = variant.getMappingFile()
                    if (null != mappingFile) {
                        String mappingFileSuffix = project.name
                        if (flavorName != null && !flavorName.isEmpty()) {
                            mappingFileSuffix += "-" + flavorName
                        }
                        mappingFileSuffix += "-mapping.txt"
                        String mappingFileName = mappingFile.getParent() + File.separator + mappingFileSuffix
                        mappingFile.renameTo(new File(mappingFileName))
                        mappingFiles.add(new File(mappingFileName))
                    }
                }
            }
        } else if (project.android.hasProperty("libraryVariants")) {
            project.android.libraryVariants.all { variant ->
                if (flavorName != null && !variant.getFlavorName().equals(flavorName)) {
                    return
                }
                if (variant.name.capitalize().contains(this.variant.name.capitalize())) {
                    File mappingFile = variant.getMappingFile()
                    if (null != mappingFile) {
                        String mappingFileSuffix = project.name
                        if (flavorName != null && !flavorName.isEmpty()) {
                            mappingFileSuffix += "-" + flavorName
                        }
                        mappingFileSuffix += "-mapping.txt"
                        String mappingFileName = mappingFile.getParent() + File.separator + mappingFileSuffix
                        mappingFile.renameTo(new File(mappingFileName))
                        mappingFiles.add(new File(mappingFileName))
                    }
                }
            }
        }
        return mappingFiles
    }

    private Vector<File> getAllMappingFiles(Project project, String flavorName) {
        Vector<File> mappingFiles = new Vector<File>()
        Vector<File> projectMappingFiles = null
        // Get debug SO files of dependent project.
        project.configurations.compile.getDependencies().withType(ProjectDependency).each {
            Project dProject = it.getDependencyProject()
            projectMappingFiles = getMappingFile(dProject, null)
            if (projectMappingFiles != null) {
                mappingFiles.addAll(projectMappingFiles)
            }
        }
        // Get mapping file of this project.
        projectMappingFiles = getMappingFile(project, flavorName)
        if (projectMappingFiles != null) {
            mappingFiles.addAll(projectMappingFiles)
        }
        return mappingFiles
    }
    /**
     * Get version name of project.
     *
     * @param variant variant of project
     * @return version name of project
     */
    private String getVersionName(Object variant) {
        String versionName = null
        if (project.android.hasProperty("applicationVariants")) {
            // Get version name by api of variant
            versionName = variant.getVersionName()
        }
        // Get version name of "defaultConfig".
        if (null == versionName || versionName.isEmpty()) {
            versionName = project.android.defaultConfig.versionName
        }
        // Get version name of "AndroidManifest.xml".
        if (null == versionName || versionName.isEmpty()) {
            versionName = new DefaultManifestParser().getVersionName(project.android.sourceSets.main.manifest.srcFile)
        }
        return versionName
    }

}
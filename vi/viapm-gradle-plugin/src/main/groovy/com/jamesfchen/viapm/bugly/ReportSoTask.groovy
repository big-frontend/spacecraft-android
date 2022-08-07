package com.jamesfchen.viapm.bugly

import com.android.builder.core.DefaultManifestParser
import com.jamesfchen.P
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction


class ReportSoTask extends DefaultTask {
    @Input
    def variant

    @TaskAction
    def report() {
        P.info("BuglyPlugin so task action start")
        if (project.bugly.appId == null) {
            project.logger.error("Please set your app id")
            return
        }
        if (project.bugly.appKey == null) {
            project.logger.error("Please set your app key")
            return
        }
        def version = getVersionName(variant)
        if (version == null) {
            project.logger.error("Failed to get version name of project" + project.getName())
            return
        }
        println "${variant.name} \n${project.bugly}"
        String flovorName = variant.getFlavorName()
        File symbolLogFile = LogFile.getLogFile(LogFile.SYMBOL_LOG_FILE_NAME, project)
        List<File> soFiles = getAllDebugSoFiles(flovorName)

        soFiles.each { sofile ->
            Report.UploadInfo uploadInfo = new Report.UploadInfo()
            uploadInfo.appId = project.bugly.appId
            uploadInfo.appKey = project.bugly.appKey
            uploadInfo.packageName = variant.applicationId
            uploadInfo.version = version
            uploadInfo.isMapping = false

//            File symtabFile = null
//            String symbolFilePath = LogFile.parseLogFile(symbolLogFile, file)
//            if (symbolFilePath != null) {
//                project.logger.info("The record of symtab file is found in symbol log file.")
//                if (new File(symbolFilePath).exists()) {
//                    symtabFile = new File(symbolFilePath)
//                } else {
//                    project.logger.warn("But the symbol file recorded does not exist.")
//                }
//            }
//            if (symtabFile == null) {
            uploadInfo.file = createSymbolFile(sofile.getAbsolutePath(), null)
//                if (symtabFile == null) {
//                    project.logger.error("Failed to create symtab file.")
//                    return
//                }
//                LogFile.logFileWithExtraInfo(symbolLogFile, file, symtabFile.getAbsolutePath())
//            }
            Report.uploadSymtab(uploadInfo)

        }
//        File uploadLogFile = LogFile.getLogFile(LogFile.UPLOAD_LOG_FILE_NAME, project)
//        uploadInfo.files.each { uploadFile ->
////            if (LogFile.checkLogFile(uploadLogFile, uploadFile)) {
////                project.logger.info("The record of symtab file is found in upload log file.")
////                return
////            }
//            if (Report.uploadSymtabFile(uploadInfo)) {
//                LogFile.logFile(uploadLogFile, uploadFile)
//            }
//        }

        P.info("BuglyPlugin so task action end\n")
    }

    File createSymbolFile(String soFilePath, String outputDirName) {
        String[] args
        if (null == outputDirName) {
            args = ["-i", soFilePath]
        } else {
            args = ["-i", soFilePath, "-o", outputDirName]
        }
        SymtabToolAndroid.main(args)
        String symtabFileName = SymtabToolAndroid.symtabFileName
        return new File(symtabFileName)
    }


    private Vector<File> getDebugSoFiles(String flavorName, Project project) {
        Vector<File> soFiles = new Vector<File>()
        if (project == null) {
            return soFiles
        }
        if (flavorName != null && !flavorName.isEmpty()) {
            flavorName += "/"
        }
        String variantFilter = "**/" + flavorName + "${variant.name}/obj/**/*.so"

        String genericFilter = "**/obj/**/*.so"
        ConfigurableFileTree collection = project.fileTree(project.buildDir) {
            include variantFilter
        }
        if (!collection.isEmpty()) {
            soFiles.addAll(collection.files)
            return soFiles
        }
        collection = project.fileTree(project.projectDir) {
            include variantFilter
        }
        if (!collection.isEmpty()) {
            soFiles.addAll(collection.files)
            return soFiles
        }
        collection = project.fileTree(project.projectDir) {
            include genericFilter
        }
        soFiles.addAll(collection.files)
        return soFiles
    }

    private Vector<File> getAllDebugSoFiles(String flavorName) {
        Vector<File> soFiles = new Vector<File>()
        // Get debug SO files of dependent project.
        project.configurations.compile.getDependencies().withType(ProjectDependency).each {
            Project dProject = it.getDependencyProject()
            soFiles.addAll(getDebugSoFiles(flavorName, dProject))
        }
        // Get debug SO files of this project.
        soFiles.addAll(getDebugSoFiles(flavorName, project))
        return soFiles
    }

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
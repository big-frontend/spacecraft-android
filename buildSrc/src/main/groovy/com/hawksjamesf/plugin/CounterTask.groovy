package com.hawksjamesf.plugin

import com.google.common.collect.ImmutableSet
import com.hawksjamesf.plugin.util.P
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CounterTask extends DefaultTask {
    public ImmutableSet<File> srcDirs
    int totals = 0
    int lines =0

    @TaskAction
    def startCounting() {
        if (srcDirs == null || srcDirs.size() == 0) return
//        dir.eachFileRecurse() { file ->
//            if (file.isDirectory()) PrintUtil.printInfo("-"*i)
//            PrintUtil.printInfo("CounterPlugin task action ${file.name}")
//        def lines = file.readLines()
//        lines.size()
//        }
//        dir.traverse {file ->
//            PrintUtil.printInfo("CounterPlugin task action ${file.name}")
//        }

//        PrintUtil.printInfo("CounterPlugin task action srcDir : ${srcDirs.size()} ")
        P.info("CounterPlugin task action start\n")
        srcDirs.each { srcDir ->
            printDirectoryTree(srcDir)
        }
        P.info("CounterPlugin task action end>>> totals : ${totals} lines\n\r")
    }

    def printDirectoryTree(File dir) {
        if (dir == null || !dir.isDirectory() || !dir.exists()) return
        int indent = 0
        StringBuilder sb = new StringBuilder()
        printDirectoryTree(dir, indent, sb);
        P.info("${dir.path}\n$sb>>> lines : ${lines} lines\n\r")
        this.lines=0

    }

    void printDirectoryTree(File dir, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(dir.getName());
        sb.append("/");
        sb.append("\n")
        dir.eachFile { theFile ->
            if (!theFile.name.contains('.DS_Store')) {
                if (theFile.isFile()) {
                    printFile(theFile, indent + 1, sb)
                } else if (theFile.isDirectory()) {
                    printDirectoryTree(theFile, indent + 1, sb)
                }
            }
        }

    }

    void printFile(File file, int indent, StringBuilder sb) {
        int lines = file.readLines().size()
        totals += lines
        this.lines +=lines
        sb.append(getIndentString(indent))
                .append("+--")
                .append(file.getName())
                .append("(lines:")
                .append(lines)
                .append(")")
                .append("\n")
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}
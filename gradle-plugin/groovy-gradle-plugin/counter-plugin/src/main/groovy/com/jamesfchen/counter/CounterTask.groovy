package com.jamesfchen.counter

import com.jamesfchen.util.P
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class CounterTask extends DefaultTask {
    public List<File> srcDirs
    @Input
    int l_totals = 0
    @Input
    int lines = 0
    @Input
    int j_l_totals = 0
    @Input
    int j_lines = 0
    @Input
    int j_f_totals = 0
    @Input
    int j_files = 0
    @Input
    int k_l_totals = 0
    @Input
    int k_lines = 0
    @Input
    int k_f_totals = 0
    @Input
    int k_files = 0
    @Input
    int c_l_totals = 0
    @Input
    int c_lines = 0
    @Input
    int c_f_totals = 0
    @Input
    int c_files = 0

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
        P.info("CounterPlugin task action end>>> totals : ${j_f_totals} java/${j_l_totals} lines, " +
                "${k_f_totals} kotlin/${k_l_totals} lines," +
                " ${c_f_totals} c or c++/${c_l_totals} lines  \n\r")
        def map = new HashMap<String, String>()
        map.put("|  java|", "|  java|${j_f_totals} |${j_l_totals}|")
        map.put("|  kotlin|", "|  kotlin|${k_f_totals}|${k_l_totals}|")
        map.put("|  c or c++|", "|  c or c++|${c_f_totals}|${c_l_totals}|")
        File file = new File(project.rootDir.path, "README.md")
        replace(file, map)
    }

    def replace(File file, HashMap<String, String> map) {
        def keys = map.keySet()
        BufferedReader br = new BufferedReader(new FileReader(file));
        CharArrayWriter tempStream = new CharArrayWriter();
        String line = null
        while ((line = br.readLine()) != null) {
            for (int i = 0; i < keys.size(); i++) {
                def k = keys[i]
                if (line.contains(k)) {
                    line = line.replace(line, map.get(k))
                    print(k + " " + line + "\n")
                    break
                }
            }
            tempStream.write(line)
            tempStream.append(System.getProperty("line.separator"));
        }
        br.close()
        FileWriter out = new FileWriter(file)
        tempStream.writeTo(out)
        out.close()

    }

    def printDirectoryTree(File dir) {
        if (dir == null || !dir.isDirectory() || !dir.exists()) return
        int indent = 0
        StringBuilder sb = new StringBuilder()
        printDirectoryTree(dir, indent, sb);
        P.info("${dir.path}\n$sb>>> ${j_files} java/${j_lines} lines, " +
                "${k_files} kotlin/${k_lines} lines," +
                "${c_files} kotlin/${c_lines} lines," +
                "\n\r")
        this.lines = 0
        this.j_lines = 0
        this.k_lines = 0
        this.j_files = 0
        this.k_files = 0
        this.c_lines = 0
        this.c_files = 0

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
        this.lines += lines
        this.l_totals += lines
        if (file.name.endsWith("kt")) {
            this.k_lines += lines
            this.k_l_totals += lines
            this.k_files += 1
            this.k_f_totals += 1
        } else if (file.name.endsWith("java")) {
            this.j_lines += lines
            this.j_l_totals += lines
            this.j_files += 1
            this.j_f_totals += +1
        } else if (file.name.endsWith("c") || file.name.endsWith("cpp") || file.name.endsWith("h")) {
            this.c_lines += lines
            this.c_l_totals += lines
            this.c_files += 1
            this.c_f_totals += +1
        }
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
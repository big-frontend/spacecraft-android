package com.jamesfchen.util


import java.util.zip.ZipFile

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * author hawksjamesf*
 * email: hawksjamesf@gmail.com
 * since Nov/02/2020  Mon
 */
class F {
//    static void replace(File file, HashMap<String, String> map) {
//        Set<String> keys = map.keySet();
//        try (BufferedReader br = new BufferedReader(new FileReader(file)); FileWriter out = new FileWriter(file)) {
//            CharArrayWriter tempStream = new CharArrayWriter();
//            String line = null;
//            while ((line = br.readLine()) != null) {
////                for (int i = 0; i < keys.size(); i++) {
////                    String k = keys[i];
////                    if (line.contains(k)) {
////                        System.out.println(k + " " + line + "\n");
////                        line = line.replace(line, map.get(k));
////                        break;
////                    }
////                }
//                tempStream.write(line);
//                tempStream.append(System.getProperty("line.separator"));
//            }
//
//            tempStream.writeTo(out);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    static void zip(File srcFile/*dir or jar*/, File destZip) {
        if (!destZip.exists()) {
            destZip.mkdirs()
        }
//        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(destZip))
//        JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(destJar))
//        jarOut.close()
//        zipOut.close()
    }

    static List<File> unzip(File zipFile, File destDir) {
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
        List<File> unzipDir=new ArrayList<>()
        new ZipFile(zipFile).withCloseable { theZip ->
            theZip.entries().each { zipEntry ->
                File file = new File(destDir, zipEntry.name)
                unzipDir.add(file)
                if (zipEntry.directory){
                    createOrExistsDir(file)
                    return
                }
                println("cjf ${zipEntry.directory} ${zipEntry.name}  ${file}")
                createOrExistsFile(file)
                file.withOutputStream { fout ->
                    fout.write(theZip.getInputStream(zipEntry).bytes)
                }
            }
        }
        return null
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

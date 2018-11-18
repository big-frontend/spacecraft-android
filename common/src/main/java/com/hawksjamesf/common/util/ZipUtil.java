package com.hawksjamesf.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/15/2018  Thu
 * <p>
 * ZipOutputStream/ZipInputStream  ZipFile ZipEntry
 * <p>
 * JarOutputStream/JarInputStream JarFile  JarEntry
 * <p>
 * GZIPOutputStream/GZIPInputStream
 */
public class ZipUtil {
    private static final int BUFFER_LEN = 8 * 1024;

    public static boolean zipFiles(final Collection<String> resFilePaths,
                                   final String zipFilePath) throws IOException {
        return zipFiles(resFilePaths, zipFilePath, null);
    }

    public static boolean zipFiles(final Collection<String> resFilePaths,
                                   final String zipFilePath,
                                   final String comment) throws IOException {
        if (resFilePaths == null || zipFilePath == null) return false;
        ZipOutputStream zos = null;
        try {

            zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
            for (String resFile : resFilePaths) {
                if (!zipFile(getFileByPath(resFile), "", zos, comment)) return false;
            }
            return true;
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtil.closeIO(zos);
            }
        }
    }

    public static boolean zipFiles(final Collection<File> resFiles,
                                   final File zipFile) throws IOException {
        return zipFiles(resFiles, zipFile, null);
    }

    public static boolean zipFiles(final Collection<File> resFiles,
                                   final File zipFile,
                                   final String comment) throws IOException {
        if (resFiles == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File resFile : resFiles) {
                if (!zipFile(resFile, "", zos, comment)) return false;
            }
            return true;
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtil.closeIO(zos);
            }
        }
    }

    public static boolean zipFile(final String resFilePath, final String zipFilePath) throws IOException {
        return zipFile(resFilePath, zipFilePath, null);
    }

    public static boolean zipFile(final String resFilePath, final String zipFilePath, final String comment) throws IOException {
        return zipFile(getFileByPath(resFilePath), getFileByPath(zipFilePath), comment);
    }

    public static boolean zipFile(final File resFile,
                                  final File zipFile) throws IOException {
        return zipFile(resFile, zipFile, null);
    }


    public static boolean zipFile(final File resFile,
                                  final File zipFile,
                                  final String comment) throws IOException {
        if (resFile == null || zipFile == null) return false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            return zipFile(resFile, "", zos, comment);
        } finally {
            if (zos != null) {
                zos.finish();
                CloseUtil.closeIO(zos);
            }
        }

    }


    /*
    递归压缩指定的路径文件或者目录
     */
    public static boolean zipFile(final File resFile, String rootPath,
                                  final ZipOutputStream zos,
                                  final String comment) throws IOException {
        rootPath += (isSpace(rootPath) ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    if (!zipFile(file, rootPath, zos, comment)) return false;
                }
            }
        } else {//压缩文件
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(resFile));
                ZipEntry entry = new ZipEntry(rootPath);
                entry.setComment(comment);
                zos.putNextEntry(entry);
                byte[] buffer = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } finally {
                CloseUtil.closeIO(is);
            }

        }

        return true;

    }

    public static List<File> unzipFile(final String zipFilePath,
                                       final String destDirPath) throws IOException {
        return unzipFileByKeyword(zipFilePath, destDirPath, null);
    }

    public static List<File> unzipFile(final File zipFile,
                                       final File destDir) throws IOException {
        return unzipFileByKeyword(zipFile, destDir, null);
    }

    public static List<File> unzipFileByKeyword(final String zipFilePath,
                                                final String destDirPath,
                                                final String keyword) throws IOException {
        return unzipFileByKeyword(getFileByPath(zipFilePath), getFileByPath(destDirPath), keyword);
    }

    public static List<File> unzipFileByKeyword(final File zipFile,
                                                final File destDir,
                                                final String keyword) throws IOException {
        if (zipFile == null || destDir == null) return null;
        List<File> files = new ArrayList<>();
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        if (isSpace(keyword)) {
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!unzipChildFile(destDir, files, zf, entry, name)) return files;
            }
        } else {

        }

        return files;

    }

    public static boolean unzipChildFile(final File destDir,
                                         final List<File> files,
                                         final ZipFile zf,
                                         final ZipEntry entry,
                                         final String entryName) throws IOException {
        String filePath = destDir + File.separator + entryName;
        File file = new File(filePath);
        files.add(file);
        if (entry.isDirectory()) {
            if (!createOrExistsDir(file)) return false;
        } else {
            if (!createOrExistsFile(file)) return false;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(zf.getInputStream(entry));
                out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] bytes = new byte[BUFFER_LEN];
                int len;
                while ((len = in.read(bytes, 0, BUFFER_LEN)) != -1) {
                    out.write(bytes, 0, len);
                }
            } finally {
                CloseUtil.closeIO(in, out);
            }
        }
        return true;
    }

    public static List<String> getFilesPath(final String zipFilePath) throws IOException {
        return getFilesPath(getFileByPath(zipFilePath));
    }

    public static List<String> getFilesPath(final File zipFile) throws IOException {
        if (zipFile == null) return null;
        ArrayList<String> paths = new ArrayList<>();
        Enumeration<? extends ZipEntry> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            paths.add(entries.nextElement().getName());
        }
        return paths;

    }

    public static List<String> getComments(final String zipFilePath) throws IOException {
        return getComments(getFileByPath(zipFilePath));
    }

    public static List<String> getComments(final File zipFile) throws IOException {
        if (zipFile == null) return null;
        ArrayList<String> comments = new ArrayList<>();
        Enumeration<? extends ZipEntry> entries = new ZipFile(zipFile).entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            comments.add(entry.getComment());
        }
        return comments;
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static boolean createOrExistsFile(final File file) {
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

    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    //whitespace:block tab newline
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) return false;
        }
        return true;
    }
}

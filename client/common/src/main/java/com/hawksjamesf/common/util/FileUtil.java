package com.hawksjamesf.common.util;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/12/2018  Mon
 */
public class FileUtil {
    private FileUtil() {
        throw new UnsupportedOperationException();
    }

    private static final String LINE_SEP = System.getProperty("line.separator");

    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    public static boolean rename(final File file, final String newName) {
        if (file == null) return false;
        if (!file.exists()) return false;
        if (isSpace(newName)) return false;
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdir());
    }

    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
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

    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        if (file.exists() && !file.delete()) return false;
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyOrMoveDir(final String srcDirPath,
                                        final String destDirPath,
                                        final OnReplaceListener listener,
                                        final boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

    public static boolean copyOrMoveDir(final File srcDir, final File destDir, final OnReplaceListener listener, final boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (listener.onReplace()) {
                if (!deleteAllInDir(destDir)) return false;
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
            }
        }

        return !isMove || deleteDir(srcDir);
    }

    private static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath,
                                          final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
    }

    public static boolean copyOrMoveFile(final File srcFile, final File destFile,
                                         final OnReplaceListener listener, final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        if (srcFile.equals(destFile)) return false;
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {
            if (listener.onReplace()) {
                if (!destFile.delete()) return false;
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return FileIOUtil.writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean copyDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, false);
    }

    public static boolean copyFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    public static boolean moveDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    public static boolean moveFile(final File srcDir, final File destDir,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcDir, destDir, listener, true);
    }

    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean moveFile(final String srcDirPath, final String destDirPath,
                                   final OnReplaceListener listener) {
        return moveFile(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean deleteFile(final String filePath) {
        return deleteFile(getFileByPath(filePath));
    }

    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();//该路径下的所有同级文件和目录
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    public static boolean deleteAllInDir(final File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter fileFilter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), fileFilter);
    }

    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter fileFilter) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file :
                    files) {
                if (fileFilter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    interface OnReplaceListener {
        boolean onReplace();
    }


    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(getFileByPath(dirPath));
    }

    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(final String dirPath, final FileFilter fileFilter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), fileFilter, false);
    }

    public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter fileFilter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (fileFilter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, fileFilter, true));
                }

            }
        }
        return list;
    }

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    public static long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            p = (bufferedInputStream.read() << 8) + bufferedInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(bufferedInputStream);
        }

        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    public static int getFileLines(final File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[1024];
            int readChars;
            if (LINE_SEP.endsWith("\n")) {//lf unix & osx \n
                while ((readChars = is.read(bytes, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (bytes[i] == '\n') ++count;
                    }
                }
            } else {//crlf windows \r\n : cr classic mac \r
                while ((readChars = is.read(bytes, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (bytes[i] == '\r') ++count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(is);
        }
        return count;
    }

    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    public static String getDirSize(final File file) {
        long len = getDirLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    public static long getDirLength(final File dir) {
        if (!isDir(dir)) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    @SuppressLint("DefaultLocale")
    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format("%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format("%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format("%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format("%.3fGB", (double) byteNum / 1073741824);
        }
    }

    public static String getFileSize(final String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    public static String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    public static String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return null;
    }

    private static byte[] getFileMD5(File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md5);
            byte[] bytes = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(bytes) > 0)) break;
            }
            md5 = dis.getMessageDigest();
            return md5.digest();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(dis);
        }
        return null;
    }

    public static String getDirName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    public static String getFileName(final File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        if (lastPoi == -1 || lastSep > lastPoi) return filePath.substring(lastSep + 1);
        return filePath.substring(lastSep + 1, lastPoi);

    }

    public static String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    public static String getFileExtension(final String filePath) {
        if (isSpace(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep > lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    private static boolean isSpace(String filePath) {
        if (filePath == null) return true;
        for (int i = 0, len = filePath.length(); i < len; ++i) {
            if (!Character.isWhitespace(filePath.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}

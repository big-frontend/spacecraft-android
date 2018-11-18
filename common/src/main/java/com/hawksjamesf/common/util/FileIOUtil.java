package com.hawksjamesf.common.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/12/2018  Mon
 * <p>
 * JDK has two sets of I/O packages:
 * 1.the Standard I/O (in package java.io), introduced since JDK 1.0 for stream-based I/O, and
 * 2.the New I/O (in packages java.nio), introduced in JDK 1.4, for more efficient buffer-based I/O.
 * <p>
 * flush():flush the remaining bytes from the output buffer.
 * <p>
 * <p>
 * io内部数据格式使用、primate data、ucs-2(16-bits 字符)
 * 外部数据格式使用：
 * - 固定长度：8-bit (e.g., US-ASCII, ISO-8859-1）、16-bit (e.g., UCS-16),
 * - 可变长度：1-4 bytes (e.g., UTF-8, UTF-16, UTF-16-BE, UTF-16-LE, GBK, BIG5)
 * <p>
 * 1byte=8bits意味着有256种排列组合，故可以存放256个字符
 * 2bye=16bits意味着有65536中排列组合，故可以存放65536个字符
 * <p>
 * java中
 * - boolean:1bit
 * - byte: 8bits
 * - char：2byte，16bits
 * - short：2byte，16bits
 * - int: 4byte, 32bits
 * - long: 8byte, 64bits
 * - float: 4byte, 32bits
 * - double: 8byte, 64bits
 * <p>
 * IO
 * <p>
 * InputStream and OutputStream(byte-to-byte) for byte-base I/O read() [0,255]
 * Reader and Writer(character-to-character) for character-based I/O.  read() [0,65535]
 * InputStreamReader and OutputStreamWriter(byte-to-character)
 * PrintStream  for printing primitives and text string(subclass System.out and System.err)
 * PrintWriter
 * <p>
 * DataInputStream and DataOutputStream :primate data
 * ObjectInputStream and ObjectOutputStream:object
 * <p>
 * RandomAccessFile
 * 1、向10G文件末尾插入指定内容，或者向指定指针位置进行插入或者修改内容。
 * 2、断点续传，使用seek()方法不断的更新下载资源的位置。
 * <p>
 * ZipInputStream and ZipOutputStream /GZIPInputStream and GZIPOutputStream
 * <p>
 * Scanner and Formatter 格式输入输出
 * <p>
 * file io:InputStream and OutputStream
 * network io:Reader and Writer ，
 * text io:  InputStreamReader and OutputStreamWriter
 * <p>
 * <p>
 * <p>
 * NIO
 * <p>
 * A java.nio.file.Path instance specifies the location of a file, or a directory, or a symbolic link. Path replaces java.io.File (of the standard I/O), which is less versatile and buggy.
 * <p>
 * java.nio.file.Paths
 * java.nio.file.Files
 */
public class FileIOUtil {
    /**
     * <p>
     * 1.Window use back-slash '\' as the directory separator,while unix/mac,linux use foreword-slash '/'
     * {@code}File.separator File.separatorChar{@code}
     * 2.Windows use semi-colon ';' as path separator to separate a list of paths; while Unixes/Mac use colon ':'.
     * {@code}File.pathSeparator;\File.pathSeparatorChar{@code}
     * 3.Windows use "\r\n" as line delimiter for text file; while Unixes use "\n" and Mac uses "\r".
     * 4.The "c:\" or "\" is called the root. Windows supports multiple roots, each maps to a drive (e.g., "c:\", "d:\"). Unixes/Mac has a single root ("\").
     */
    private static final String LINE_SEP = System.getProperty("line.separator");//System.getProperty("file.encoding"

    //对于文件copy来说，建议增大buffer；对于读取文件来说，不建议buffer过大，容易让费内存。可以这么理解，文件copy时数据在buffer存留时间不会很长，其相当于一个快递收发站，立马就写入到另外一个文件。但是对于读取文件来说，buffer中的数据停留较长，比如音视频播放，存有大数据的buffer提供给其他的buffer使用而不是写入到文件中，在内存停留较长。
    //对于buffer我们可以手动创建其大小，也可以交给jre，通过BufferedOutputStream类完成。
    private static final int sBufferSize = 8192;//8k byte-buffer

    public static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return writeFileFromIS(getFileByPath(filePath), is, false);
    }

    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    public static boolean writeFileFromIS(final File file, final InputStream is) {
        return writeFileFromIS(file, is, false);
    }

    public static boolean writeFileFromIS(final File file, final InputStream is, final boolean append) {
        if (!createOrExitsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] bytes = new byte[sBufferSize];
            int len;
            while ((len = is.read(bytes, 0, sBufferSize)) != -1) {
                os.write(bytes, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtil.closeIO(is, os);
        }
    }

    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, final boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes, final boolean append) {
        if (bytes == null || bytes.length == 0 || !createOrExitsFile(file)) return false;
        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            os.write(bytes);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtil.closeIO(os);
        }
    }

    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        if (!createOrExitsFile(file) || bytes == null || bytes.length == 0) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtil.closeIO(fc);
        }
    }

    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        if (bytes == null || bytes.length == 0 || !createOrExitsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtil.closeIO(fc);
        }
    }

    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    public static boolean writeFileFromString(final String filePath, final String content, final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean writeFileFromString(final File file, final String content, final boolean append) {
        if (!createOrExitsFile(file) || content == null || content.length() == 0) return false;
        //JDK 1.7 introduces a new try-with-resources syntax
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }// Automatically closes all opened resource in try (...).
    }

    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }


    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    public static List<String> readFile2List(final String filePath, final int st, final int end, final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    public static List<String> readFile2List(final File file, final String charsetName) {
        //0x7FFFFFFF==Long.MAX_VALUE
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);

    }

    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

    public static List<String> readFile2List(final File file, final int st, final int end, final String charsetName) {
        if (!isFileExits(file)) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            ArrayList<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }

            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(reader);
        }
    }

    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    public static String readFile2String(final File file, final String charsetName) {
        if (!isFileExits(file)) return null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEP).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(reader);
        }
    }

    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExits(file)) return null;
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[sBufferSize];
            int len;
            while ((len = fis.read(bytes, 0, sBufferSize)) != -1) {
                baos.write(bytes);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExits(file)) return null;
        try (FileChannel fc = new RandomAccessFile(file, "r").getChannel()) {
            ByteBuffer allocate = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(allocate)) > 0)) break;
            }
            return allocate.array();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFile2BytesByMap(final String filePath){
        return readFile2BytesByMap(getFileByPath(filePath));
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static byte[] readFile2BytesByMap(final File file){
        if (!isFileExits(file)) return null;
        try(FileChannel fc=new RandomAccessFile(file,"r").getChannel()) {
            int size=(int)fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] bytes = new byte[size];
            mbb.get(bytes,0,size);
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isFileExits(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);

    }

    public static boolean createOrExitsFile(final String filePath) {
        return createOrExitsFile(getFileByPath(filePath));
    }

    public static boolean createOrExitsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExitsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExitsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());

    }

    public static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}

@file:JvmName("FileIOUtil")
package com.electrolytej.util

import android.system.ErrnoException
import android.system.Os
import android.system.OsConstants
import com.electrolytej.base.BuildConfig
import java.io.FileDescriptor
import java.io.IOException
import java.nio.ByteBuffer

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
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

/**
 *
 *
 * 1.Window use back-slash '\' as the directory separator,while unix/mac,linux use foreword-slash '/'
 * ``File.separator File.separatorChar``
 * 2.Windows use semi-colon ';' as path separator to separate a list of paths; while Unixes/Mac use colon ':'.
 * ``File.pathSeparator;\File.pathSeparatorChar``
 * 3.Windows use "\r\n" as line delimiter for text file; while Unixes use "\n" and Mac uses "\r".
 * 4.The "c:\" or "\" is called the root. Windows supports multiple roots, each maps to a drive (e.g., "c:\", "d:\"). Unixes/Mac has a single root ("\").
 */
val LINE_SEP = System.getProperty("line.separator") //System.getProperty("file.encoding"

//对于文件copy来说，建议增大buffer；对于读取文件来说，不建议buffer过大，容易让费内存。可以这么理解，文件copy时数据在buffer存留时间不会很长，其相当于一个快递收发站，立马就写入到另外一个文件。但是对于读取文件来说，buffer中的数据停留较长，比如音视频播放，存有大数据的buffer提供给其他的buffer使用而不是写入到文件中，在内存停留较长。
//对于buffer我们可以手动创建其大小，也可以交给jre，通过BufferedOutputStream类完成。
const val sBufferSize = 8192 //8k byte-buffer

@Throws(IOException::class)
fun readFully(fd: FileDescriptor?, to: ByteBuffer?) {


}
@Throws(IOException::class)
fun writeFully(fd: FileDescriptor?, from: ByteBuffer?) {
    if (fd == null || from == null) return
    // ByteBuffer position is not updated as expected by Os.write() on old Android versions, so
    // count the remaining bytes manually.
    // See <https://github.com/Genymobile/scrcpy/issues/291>.
    var remaining = from.remaining()
    while (remaining > 0) {
        try {
            val w = Os.write(fd, from)
            if (BuildConfig.DEBUG && w < 0) {
                // w should not be negative, since an exception is thrown on error
                throw java.lang.AssertionError("Os.write() returned a negative value ($w)")
            }
            remaining -= w
        } catch (e: ErrnoException) {
            if (e.errno != OsConstants.EINTR) {
                throw IOException(e)
            }
        }
    }
}

@Throws(IOException::class)
fun writeFully(fd: FileDescriptor?, buffer: ByteArray?, offset: Int, len: Int) {
    writeFully(fd, ByteBuffer.wrap(buffer, offset, len))
}
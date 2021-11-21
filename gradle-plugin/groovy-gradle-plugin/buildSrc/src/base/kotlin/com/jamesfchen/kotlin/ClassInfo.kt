package com.jamesfchen.kotlin

import java.io.File
import java.io.InputStream

/**
 * Copyright ® $ 2021
 * All right reserved.

 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 7月/08/2021  周四
 */
class ClassInfo {
    var mather: File//jar or dir
    var classFile: File? = null
    var classStream: InputStream? = null
    var canonicalName: String

    constructor(mather: File, classStream: InputStream, canonicalName: String) {
        this.mather = mather
        this.classStream = classStream
        this.canonicalName = canonicalName
    }

    constructor(mather: File, classFile: File, canonicalName: String) {
        this.mather = mather
        this.classFile = classFile
        this.canonicalName = canonicalName
    }

    override fun toString(): String {
        return if (classFile != null) {
            val info = "ClassInfo{" +
                    "mather=" + mather +
                    ", classFile=" + classFile +
                    ", canonicalName='" + canonicalName + '\'' +
                    '}';
            "Class In Dir $info"
        } else {
            val info = "ClassInfo{" +
                    "mather=" + mather +
                    ", classStream=" + classStream +
                    ", canonicalName='" + canonicalName + '\'' +
                    '}';
            "Class In Jar $info"
        }

    }
}
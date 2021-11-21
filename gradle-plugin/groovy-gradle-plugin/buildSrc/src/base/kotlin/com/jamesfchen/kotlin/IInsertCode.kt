package com.jamesfchen.kotlin

import java.io.File
import java.io.InputStream

interface IInsertCode {
    fun onInsertCodeBegin()

    //     byte[] onInsertCodeInDir(ClassInfo info) { return null }
//     byte[] onInsertCodeInJar(ClassInfo info) { return null }
    fun onInsertCode(mather: File, classStream: InputStream, canonicalName: String): ByteArray?
    fun onInsertCodeEnd()
}
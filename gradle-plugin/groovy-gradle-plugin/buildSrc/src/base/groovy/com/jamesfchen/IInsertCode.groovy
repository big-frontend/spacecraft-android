package com.jamesfchen

interface IInsertCode {
    void onInsertCodeBegin()
//     byte[] onInsertCodeInDir(ClassInfo info) { return null }
//     byte[] onInsertCodeInJar(ClassInfo info) { return null }
    byte[] onInsertCode(File mather, InputStream classStream, String canonicalName)
    void onInsertCodeEnd()
}
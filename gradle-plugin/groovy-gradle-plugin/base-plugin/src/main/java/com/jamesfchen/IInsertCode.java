package com.jamesfchen;

import java.io.File;
import java.io.InputStream;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/28/2021  Sun
 */
public interface IInsertCode {
    void onInsertCodeBegin();
    //     byte[] onInsertCodeInDir(ClassInfo info) { return null }
//     byte[] onInsertCodeInJar(ClassInfo info) { return null }
    byte[] onInsertCode(File mather, InputStream classStream, String canonicalName);
    void onInsertCodeEnd();
}
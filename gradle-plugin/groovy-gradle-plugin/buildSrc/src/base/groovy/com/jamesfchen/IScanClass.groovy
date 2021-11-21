package com.jamesfchen

interface IScanClass {
    void onScanBegin()
    void onScanClassInDir(ClassInfo info)
    void onScanClassInJar(ClassInfo info)
    void onScanEnd()
}
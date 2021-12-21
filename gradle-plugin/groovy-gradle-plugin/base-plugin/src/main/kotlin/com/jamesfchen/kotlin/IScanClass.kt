package com.jamesfchen.kotlin

interface IScanClass {
    fun onScanBegin()
    fun onScanClassInDir( info:ClassInfo)
    fun onScanClassInJar(info:ClassInfo)
    fun onScanEnd()
}
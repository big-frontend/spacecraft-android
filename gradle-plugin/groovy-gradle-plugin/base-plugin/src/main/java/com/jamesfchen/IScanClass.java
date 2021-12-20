package com.jamesfchen;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/28/2021  Sun
 */
public interface IScanClass {
    public void onScanBegin();

    public void onScanClassInDir(ClassInfo info);

    public void onScanClassInJar(ClassInfo info);

    public void onScanEnd();
}
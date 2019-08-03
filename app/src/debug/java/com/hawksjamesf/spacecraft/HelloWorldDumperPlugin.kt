package com.hawksjamesf.spacecraft

import com.facebook.stetho.dumpapp.DumperContext
import com.facebook.stetho.dumpapp.DumperPlugin

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/03/2019  Sat
 */
class HelloWorldDumperPlugin : DumperPlugin {
    companion object {
        const val NAME = "HelloWorldDumperPlugin";
    }

    override fun dump(dumpContext: DumperContext?) {

    }

    override fun getName() = NAME
}
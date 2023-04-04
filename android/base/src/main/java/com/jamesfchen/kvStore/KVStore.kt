package com.jamesfchen.kvStore

import android.content.Context
import androidx.annotation.GuardedBy
import java.io.File

internal class KVStore(val storeFile:File) {
    companion object {
        val objectLock: Any = Any()

        @GuardedBy("objectLock")
        @Volatile
        private var sInstance: KVStore? = null

        fun getInstance(cxt:Context,fileName:String): KVStore {
            return sInstance ?: synchronized(objectLock) {
                if (sInstance == null) {
                    sInstance = KVStore(File(cxt.filesDir,"KVStore/${fileName}"))
                }
                sInstance!!
            }
        }
    }
}

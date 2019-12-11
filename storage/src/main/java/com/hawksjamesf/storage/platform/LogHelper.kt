package com.hawksjamesf.storage.platform

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

class LogHelper : SQLiteOpenHelper {
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int, errorHandler: DatabaseErrorHandler?) : super(context, name, factory, version, errorHandler)

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(context: Context?, name: String?, version: Int, openParams: SQLiteDatabase.OpenParams) : super(context, name, version, openParams)

    companion object {
        val any = Any()
        private var instance: LogHelper? = null
        fun getInstance(context: Context?): LogHelper? {
            synchronized(any::class.java) {
                if (instance == null) {
                    instance = LogHelper(context, LogContract.DB_NAME, null, LogContract.DB_VERSION)
                }
                return instance
            }
        }

        fun getDB(context: Context?, writable: Boolean = true): SQLiteDatabase? {
            return if (writable) {
                getInstance(context)?.writableDatabase
            } else {
                getInstance(context)?.readableDatabase
            }
        }

    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(LogContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
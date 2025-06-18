package com.electrolytej.tool.platform

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

class LogDBHelper : SQLiteOpenHelper {
    constructor(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : super(context, name, factory, version)

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int,
        errorHandler: DatabaseErrorHandler?
    ) : super(context, name, factory, version, errorHandler)

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(
        context: Context?,
        name: String?,
        version: Int,
        openParams: SQLiteDatabase.OpenParams
    ) : super(context, name, version, openParams)

    companion object {
        val any = Any()

        private var instance: LogDBHelper? = null
        fun getInstance(context: Context?): LogDBHelper? {
            synchronized(any::class.java) {
                if (instance == null) {
                    instance =
                        LogDBHelper(context, LogDBContract.DB_NAME, null, LogDBContract.DB_VERSION)
                }
                return instance
            }
        }

        fun getDB(context: Context? = null, writable: Boolean = true): SQLiteDatabase? {
            return if (writable) {
                getInstance(context)?.writableDatabase
            } else {
                getInstance(context)?.readableDatabase
            }
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(LogDBContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
    }
}
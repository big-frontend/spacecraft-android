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

    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(LogContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
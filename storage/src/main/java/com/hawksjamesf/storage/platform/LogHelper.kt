package com.hawksjamesf.storage.platform

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.provider.BaseColumns
import android.util.Log
import androidx.annotation.RequiresApi

class LogHelper : SQLiteOpenHelper {
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int, errorHandler: DatabaseErrorHandler?) : super(context, name, factory, version, errorHandler)

    @RequiresApi(Build.VERSION_CODES.P)
    constructor(context: Context?, name: String?, version: Int, openParams: SQLiteDatabase.OpenParams) : super(context, name, version, openParams)

    companion object {
        val any = Any()
        fun init(context: Context?): LogHelper? = getInstance(context)

        private var instance: LogHelper? = null
        fun getInstance(context: Context?): LogHelper? {
            synchronized(any::class.java) {
                if (instance == null) {
                    instance = LogHelper(context, LogContract.DB_NAME, null, LogContract.DB_VERSION)
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

    fun save() {
        val db = getDB()
        db?.delete(LogContract.LogEntry.TABLE_NAME, null, null)
        repeat(10) {
            val values = ContentValues()
            values.put(LogContract.LogEntry.COLUMNS_SERVICE_CODE, it)
            values.put(LogContract.LogEntry.COLUMNS_ENTRY_TYPE, "request")
            db?.insertOrThrow(LogContract.LogEntry.TABLE_NAME, null, values)
        }
    }

    fun query() {
        val db = getDB()
        val cursor = db?.query(LogContract.LogEntry.TABLE_NAME,
                null,
                null,null,
//                "${LogContract.LogEntry.COLUMNS_ENTRY_TYPE}=?", arrayOf("request"),
                null, null, null, null)
        Log.d("query", "count:${cursor?.count} ${cursor?.columnCount}")
        if (cursor?.moveToFirst() == true && cursor.count > 0) {
            do {
                val entrytype = cursor.getString(cursor.getColumnIndex(LogContract.LogEntry.COLUMNS_ENTRY_TYPE))
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                Log.d("query", "entiry:$entrytype ")
            } while (cursor?.moveToNext())

            cursor.close()
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(LogContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}
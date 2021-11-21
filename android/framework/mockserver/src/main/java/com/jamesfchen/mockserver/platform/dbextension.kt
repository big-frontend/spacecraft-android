package com.jamesfchen.mockserver.platform

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/16/2019  Mon
 */

fun SQLiteDatabase?.save(values: ContentValues) {
    if (this == null) return
//    this.delete(LogDBContract.TABLE_NAME, null, null)
    insertOrThrow(LogDBContract.TABLE_NAME, null, values)
}

fun SQLiteDatabase?.query() {
    if (this == null) return
    val cursor = query(LogDBContract.TABLE_NAME,
            null,
            null, null,
//                "${LogContract.LogEntry.COLUMNS_ENTRY_TYPE}=?", arrayOf("request"),
            null, null, null, null)
    if (cursor?.moveToFirst() == true && cursor.count > 0) {
        do {
            val entrytype = cursor.getString(cursor.getColumnIndex(LogDBContract.LogEntity.COLUMNS_ENTITY_TYPE))
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
        } while (cursor.moveToNext())
        cursor.close()
    }
}
package com.hawksjamesf.storage.platform

import android.provider.BaseColumns

object LogContract {
    const val DB_NAME = "log.db"
    const val DB_VERSION = 1
    const val SQL_CREATE_ENTRIES = "CREATE TABLE ${LogEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${LogEntry.COLUMNS_SERVICE_CODE} INTEGER," +
//                    "${LogEntry.COLUMNS_SERVICE_CODE} REAL," +//浮点类型
            "${LogEntry.COLUMNS_ENTRY_TYPE} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${LogEntry.TABLE_NAME}"

    object LogEntry : BaseColumns {
        const val TABLE_NAME = "log_table"
        const val COLUMNS_SERVICE_CODE="service_code"
        const val COLUMNS_ENTRY_TYPE = "entry_type"//request response
    }


}
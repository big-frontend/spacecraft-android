package com.jamesfchen.mockserver.platform

import android.net.Uri
import android.provider.BaseColumns

object LogDBContract {
    const val AUTHORITY = "com.hawksjamesf.storage.log"
    val CONTENT_URI = Uri.parse("content://$AUTHORITY")
    const val DB_NAME = "log_db"
    const val DB_VERSION = 1
    const val TABLE_NAME = "log_table"
    const val SQL_CREATE_ENTRIES = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${LogEntity.COLUMNS_SERVICE_CODE} INTEGER," +
//                    "${LogEntry.COLUMNS_SERVICE_CODE} REAL," +//浮点类型
            "${LogEntity.COLUMNS_ENTITY_TYPE} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    object LogEntity : BaseColumns {
        const val COLUMNS_ID = BaseColumns._ID
        const val COLUMNS_SERVICE_CODE = "service_code"
        const val COLUMNS_ENTITY_TYPE = "entity_type"//request response
    }


}
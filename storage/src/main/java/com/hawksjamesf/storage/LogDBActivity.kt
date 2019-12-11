package com.hawksjamesf.storage

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hawksjamesf.storage.platform.LogContract
import com.hawksjamesf.storage.platform.LogHelper

class LogDBActivity : AppCompatActivity() {
    lateinit var logHelper: LogHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "log db activity"
        textView.id = R.id.tv_text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        setContentView(textView)
        logHelper = LogHelper(this, LogContract.DB_NAME, null, LogContract.DB_VERSION)
        val writableDatabase = logHelper.writableDatabase
        repeat(10) {
            val values = ContentValues()
            values.put(LogContract.LogEntry.COLUMNS_SERVICE_CODE, it)
            values.put(LogContract.LogEntry.COLUMNS_ENTRY_TYPE, "request")
            writableDatabase.insertOrThrow(LogContract.LogEntry.TABLE_NAME, null, values)
        }
//        runBlocking {
//            delay(1_000_000)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logHelper.close()
    }
}
package com.hawksjamesf.storage

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hawksjamesf.storage.platform.LogContract
import com.hawksjamesf.storage.platform.LogHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LogDBActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView=TextView(this)
        textView.text = "log db activity"
        textView.id = R.id.tv_text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20F)
        setContentView(textView)
        val logHelper = LogHelper(this,LogContract.DB_NAME,null,LogContract.DB_VERSION)
        val writableDatabase = logHelper.writableDatabase
        repeat(10){
        val values=ContentValues()
            values.put(LogContract.LogEntry.COLUMNS_SERVICE_CODE,it)
            values.put(LogContract.LogEntry.COLUMNS_ENTRY_TYPE,"request")
            writableDatabase.insertOrThrow(LogContract.LogEntry.TABLE_NAME,null,values)
        }
        Log.d("cjf","onCreate:${logHelper}")
        runBlocking {
            delay(1_000_000)
        }
    }
}
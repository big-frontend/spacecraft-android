package com.hawksjamesf.storage

import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hawksjamesf.storage.platform.LogHelper

class LogDBActivity : AppCompatActivity() {
    var logHelper: LogHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "log db activity"
        textView.id = R.id.tv_text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        setContentView(textView)
        logHelper = LogHelper.init(this)
        logHelper?.save()
        logHelper?.query()
//        runBlocking {
//            delay(1_000_000)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logHelper?.close()
    }
}
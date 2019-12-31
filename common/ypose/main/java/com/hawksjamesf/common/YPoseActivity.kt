package com.hawksjamesf.common

import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
@Keep
class YPoseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "y pose  activity"
        textView.id = R.id.tv_text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        setContentView(textView)
        textView.text = stringFromJNI()
        textView.setOnClickListener {
            NetClient.getInstance().sendRequest()
        }
    }

    @Keep
    external fun stringFromJNI(): String
    fun stringFromJava()="string  from java"

    companion object {

        init {
            System.loadLibrary("hotfix")
        }
    }
}

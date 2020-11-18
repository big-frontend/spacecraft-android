package com.hawksjamesf.yposed

import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yposed.*

class YPosedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)
        bt_hook_frida.setOnClickListener {
            bt_hook_frida.text = stringFromJNI()
        }

    }

    fun stringFromJava()="string  from java"
    @Keep
    external fun stringFromJNI(): String

    companion object {

        init {
            System.loadLibrary("hawks")
        }
    }
}

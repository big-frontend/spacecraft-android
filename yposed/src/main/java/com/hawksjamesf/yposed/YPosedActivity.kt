package com.hawksjamesf.yposed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class YPosedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yposed)

    }

    fun stringFromJava()="string  from java"

    companion object {

        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
    }
}

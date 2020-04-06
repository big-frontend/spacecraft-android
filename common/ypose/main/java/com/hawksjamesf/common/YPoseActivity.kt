package com.hawksjamesf.common

import android.os.Bundle
import android.util.Log
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
        val custView=CustView(this)
        val custViewField=custView.javaClass.getDeclaredField("custTag")
        custViewField.isAccessible=true
        custViewField.set(custView,"CustView")
        Log.d("hawks", "onCreate: ${custViewField.get(custView)}")
        val basecustViewField=custView.javaClass.superclass?.getDeclaredField("basecustTag")
        basecustViewField?.isAccessible=true
        basecustViewField?.set(custView,"BaseCustView")
        Log.d("hawks", "onCreate: ${basecustViewField?.get(custView)}")
        val DEFAULT_INTERVALField=custView.javaClass.superclass?.superclass?.getDeclaredField("mUserPresent")
        DEFAULT_INTERVALField?.isAccessible=true
        basecustViewField?.set(custView,false)
        Log.d("hawks", "onCreate: ${DEFAULT_INTERVALField?.get(custView)}")

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

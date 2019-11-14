package com.hawksjamesf.uicomponent

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun startActivity(context: Context){
            val intent = Intent(context,FirstActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        frameLayout.setBackgroundColor(Color.DKGRAY)
        val bt = Button(this)
        bt.text = "first"
        bt.isAllCaps = false
        bt.setOnClickListener {

            SecondaryActivity.startActivity(this@FirstActivity)
        }
        frameLayout.addView(bt,ViewGroup.LayoutParams.MATCH_PARENT,200)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        setContentView(frameLayout, lp)
    }
}
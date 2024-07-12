package com.jamesfchen.bundle2.page.launchmode

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Measure
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.bundle2.R

class Jump2SingleTaskManifestActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun startActivity(context: Context){
            val intent = Intent(context, Jump2SingleTaskManifestActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt = Button(this)
        bt.text = "jump to single task[manifest]"
        bt.isAllCaps = false
        bt.setOnClickListener {
            SingleTaskInManifestActivity.startActivity(this@Jump2SingleTaskManifestActivity)
        }
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.CENTER
        findViewById<FrameLayout>(android.R.id.content).setBackgroundColor(Color.BLUE)
        setContentView(bt,lp)
    }
}
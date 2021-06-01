package com.jamesfchen.uicomponent

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class SecondaryActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, SecondaryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        frameLayout.setBackgroundColor(Color.RED)
        val bt = Button(this)
        bt.text = "secondary"
        bt.isAllCaps = false
        bt.setOnClickListener {
            ThirdActivity.startActivity(this@SecondaryActivity)
        }
        frameLayout.addView(bt, ViewGroup.LayoutParams.MATCH_PARENT, 200)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        setContentView(frameLayout, lp)
    }
}
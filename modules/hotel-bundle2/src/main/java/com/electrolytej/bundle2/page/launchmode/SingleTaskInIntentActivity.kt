package com.electrolytej.bundle2.page.launchmode

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class SingleTaskInIntentActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, SingleTaskInIntentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(Constants.TAG,"onNewIntent:${intent?.toString()}")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constants.TAG,"onCreate:${intent?.toString()}")
        val bt = Button(this)
        bt.text = "single task in intent"
        bt.isAllCaps = false
        bt.setOnClickListener {
            Jump2SingleTaskInIntentActivity.startActivity(this@SingleTaskInIntentActivity)
        }
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.CENTER
        findViewById<FrameLayout>(android.R.id.content).setBackgroundColor(Color.RED)
        setContentView(bt, lp)
    }

    override fun onStart() {
        super.onStart()
        Log.d(Constants.TAG,"onStart:${intent?.toString()}")
    }
}
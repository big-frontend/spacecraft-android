package com.electrolytej.bundle2.page.launchmode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout


/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/24/2020  Mon
 *
 */
class SingleInstanceActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, SingleInstanceActivity::class.java)
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
        bt.text = "single instance in manifest"
        bt.isAllCaps = false
        bt.setOnClickListener {
            Jump2SingleInstanceManifestActivity.startActivity(this@SingleInstanceActivity)
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
package com.jamesfchen.bundle2.launchmode

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.bundle2.databinding.ActivityWebviewBinding
import android.app.ActivityManager
import android.app.ActivityManager.AppTask
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
        bt.text = "single instance in intent"
        bt.isAllCaps = false
        bt.setOnClickListener {
            Jump2SingleTaskInIntentActivity.startActivity(this@SingleInstanceActivity)
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
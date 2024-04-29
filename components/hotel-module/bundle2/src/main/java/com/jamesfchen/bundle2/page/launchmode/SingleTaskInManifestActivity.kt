package com.jamesfchen.bundle2.page.launchmode

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.bundle2.launchmode.Constants.TAG

class SingleTaskInManifestActivity : AppCompatActivity() {
    companion object{
        @JvmStatic
        fun startActivity(context: Context){
            val intent = Intent(context, SingleTaskInManifestActivity::class.java)

            context.startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG,"onNewIntent:${intent?.toString()}")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt = Button(this)
        bt.text = "single task in manifest"
        bt.isAllCaps = false
        bt.setOnClickListener {
            Jump2SingleTaskManifestActivity.startActivity(this@SingleTaskInManifestActivity)
        }
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.CENTER
        findViewById<FrameLayout>(android.R.id.content).setBackgroundColor(Color.CYAN)
        setContentView(bt, lp)
    }
}
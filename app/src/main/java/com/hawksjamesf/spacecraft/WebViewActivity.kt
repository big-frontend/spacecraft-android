package com.hawksjamesf.spacecraft

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/24/2020  Mon
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.getStringExtra("url")
        wv.loadUrl(url)
    }
}
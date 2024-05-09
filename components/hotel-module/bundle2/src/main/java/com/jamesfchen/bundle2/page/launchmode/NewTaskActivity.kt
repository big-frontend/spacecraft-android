package com.jamesfchen.bundle2.page.launchmode

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
import com.jamesfchen.base.databinding.ActivityWebviewBinding


/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/24/2020  Mon
 */
class NewTaskActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val uri = Uri.parse("ispacecraft://www.spacecraft.com/web/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra("url", "https://www.baidu.com")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(Constants.TAG,"onNewIntent:${intent?.toString()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(Constants.TAG,"onCreate:${intent?.toString()}")
        intent.getStringExtra("url")?.let { url ->
            binding.wv.loadUrl(url)
            binding.wv.webViewClient = object : WebViewClient() {
                override fun onReceivedSslError(
                    view: WebView,
                    handler: SslErrorHandler,
                    error: SslError
                ) {
                    //handler.cancel(); 默认的处理方式，WebView变成空白页
                    handler.proceed()
                    //handleMessage(Message msg); 其他处理
                }
                // 这行代码一定加上否则效果不会出现
            }
            binding.wv.settings.javaScriptEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(Constants.TAG,"onStart:${intent?.toString()}")
    }
}
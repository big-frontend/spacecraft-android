package com.jamesfchen.h5container

import android.R.attr.height
import android.R.attr.width
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ScreenUtils
import com.jamesfchen.base.databinding.ActivityWebviewBinding


/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/24/2020  Mon
 */
open class WebViewActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            //        Fragment fragment = WebViewFragment.newInstance("https://i.meituan.com/c/ZDg0Y2FhNjMt");
//        FragmentUtils.add(getSupportFragmentManager(),fragment,android.R.id.content);
//        FragmentUtils.show(fragment);
            val uri = Uri.parse("ispacecraft://www.spacecraft.com/web/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.putExtra("url", "https://i.meituan.com/c/ZDg0Y2FhNjMt")
            intent.putExtra("url", "https://i.meituan.com/c1/ZDg0Y2FhNjMt")/**/
            context.startActivity(intent)
        }
    }

    @JavascriptInterface
    fun nativeSay(): String {
        Log.d("cjf", " native say calling from js")
        return "sb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("url")?.let { url ->
//            val renderer: BaseGLRenderer = DefaultRenderer(
//                this,
//                ScreenUtils.getAppScreenWidth(),
//                ScreenUtils.getAppScreenHeight()
//            )
//            binding.glSurfaceView.setEGLContextClientVersion(2);
//            binding.glSurfaceView.setRenderer(renderer)
//            binding.wv.setViewToGLRenderer(renderer)
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
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.lpi.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Toast.makeText(this@WebViewActivity, "url:${request?.url}", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(Intent.ACTION_VIEW, request?.url)
                    startActivity(i)
                    return true
//                    return super.shouldOverrideUrlLoading(view, request)

                }
            }
            binding.wv.settings.javaScriptEnabled = true
            binding.wv.settings.javaScriptCanOpenWindowsAutomatically = true
            binding.wv.webChromeClient = WebChromeClient()
            binding.wv.addJavascriptInterface(this@WebViewActivity, "myActivity")
            binding.title.setOnClickListener {
                binding.wv.evaluateJavascript("javascript:jsAlert()") {
                    Log.d("cjf", "jsAlert ${it}")
                }
            }
        }
//        binding.title.setImageBitmap()
    }
}
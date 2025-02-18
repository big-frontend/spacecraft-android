package com.electrolytej.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ResourceUtils
import com.electrolytej.base.databinding.ActivityWebviewBinding
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient


open class WebActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WebViewActivity"
        @JvmStatic
        fun startActivity(context: Context) {
            //        Fragment fragment = WebViewFragment.newInstance("https://i.meituan.com/c/ZDg0Y2FhNjMt");
//        FragmentUtils.add(getSupportFragmentManager(),fragment,android.R.id.content);
//        FragmentUtils.show(fragment);
            val uri = Uri.parse("ispacecraft://www.spacecraft.com/web/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra("url", "https://i.meituan.com/c1/ZDg0Y2FhNjMt")
            context.startActivity(intent)
        }
    }

    val binding: ActivityWebviewBinding by lazy {
        return@lazy ActivityWebviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        intent.getStringExtra("url")?.let { url ->
            binding.wv.loadUrl(url)
            binding.wv.webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, request)
                }
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    p2: SslError?
                ) {
                    //handler.cancel(); 默认的处理方式，WebView变成空白页
                    handler?.proceed()
                    //handleMessage(Message msg); 其他处理
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
//                    binding.lpi.visibility = View.GONE
                    binding.wv.evaluateJavascript(ResourceUtils.readAssets2String("VideoHelper.js"),null);
                    binding.wv.evaluateJavascript("javascript:videoCustomerJs()",null)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Toast.makeText(this@WebActivity, "url:${request?.url}", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(Intent.ACTION_VIEW, request?.url)
                    startActivity(i)
                    return true
//                    return super.shouldOverrideUrlLoading(view, request)

                }
            }
            binding.wv.settings.allowFileAccess = true
//            binding.wv.settings.allowFileAccessFromFileURLs = true
//            binding.wv.settings.allowUniversalAccessFromFileURLs = true

            binding.wv.settings.domStorageEnabled = true
            binding.wv.settings.databaseEnabled = true
//            binding.wv.settings.databasePath = ""
            binding.wv.settings.javaScriptEnabled = true
            binding.wv.settings.javaScriptCanOpenWindowsAutomatically = true
            binding.wv.webChromeClient = WebChromeClient()
//            binding.wv.addJavascriptInterface(nativeBridge, "NativeBridge")
//            binding.wv.addJavascriptInterface(nativeBridge, "Android")
            binding.wv.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                Log.d(TAG, "url:${url}")
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
            }
            binding.bt.setOnClickListener {
                binding.wv.evaluateJavascript("javascript:jsAlert()") {
                    Log.d(TAG, "jsAlert ${it}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.wv.removeJavascriptInterface("NativeBridge")
    }
}
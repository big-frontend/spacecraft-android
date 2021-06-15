package com.jamesfchen.myhome

import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_webview.*

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Aug/25/2020  Tue
 */
class WebViewFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(url: String): Fragment {
            val args = Bundle()
            args.putString("url", url)
            val fragment = WebViewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        wv.loadUrl(url!!)
        wv.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {

//handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed()
//handleMessage(Message msg); 其他处理
            }

// 这行代码一定加上否则效果不会出现
        }
        wv.settings.javaScriptEnabled = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
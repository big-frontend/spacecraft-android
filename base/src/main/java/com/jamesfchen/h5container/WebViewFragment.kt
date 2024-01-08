package com.jamesfchen.h5container

import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.jamesfchen.base.databinding.FragmentWebviewBinding

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
    lateinit var binding: FragmentWebviewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        binding.wv.loadUrl(url!!)
        binding.wv.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {

//handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed()
//handleMessage(Message msg); 其他处理
            }

// 这行代码一定加上否则效果不会出现
        }
        binding.wv.settings.javaScriptEnabled = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
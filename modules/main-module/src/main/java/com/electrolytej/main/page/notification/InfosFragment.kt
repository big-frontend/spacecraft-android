package com.electrolytej.main.page.notification

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.electrolytej.main.databinding.MainFragmentInfosBinding
import com.jamesfchen.pay.CPay
import com.jamesfchen.pay.IPayCallback
import com.jamesfchen.pay.WXPayInfo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL

class InfosFragment : Fragment() {
    //main-module模块的fragment_infos 与 hotel-module的fragment_infos资源会冲突，所以重命名为main_fragment_infos
    lateinit var binding: MainFragmentInfosBinding
    val infoViewModel: InfosViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = MainFragmentInfosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            val wb = WebView(requireContext())
            wb.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("InfosFragment", "onPageStarted url:${url}")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d("InfosFragment", "onPageFinished url:${url}")
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("InfosFragment", "url1:${url}")
                    if (url?.toString()?.endsWith(".apk") == true) {
                        Toast.makeText(requireContext(), "url:${url}", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    }
                    return super.shouldOverrideUrlLoading(view, url)
                }
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
//                    1. 重定向触发
//                    2.a标签触发
                    Log.d("InfosFragment", "url2:${request?.url}")
                    if (request?.url?.toString()?.endsWith(".apk") == true) {
                        Toast.makeText(requireContext(), "url:${request.url}", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
//            binding.root.addView(wb,1,0)
            binding.btText.setOnClickListener{
                //302 (Content-Type, text/html;charset=utf-8), (Expires, Thu, 01 Jan 1970 00:00:00 GMT), (Location, https://dl-1251299587.file.myqcloud.com/myqcloud/yuedan/yuedan_tuiguang_0058_824cf65ac1db1a3e1.apk), (Server, Jetty(9.3.14.v20161028)), (Set-Cookie, browserid=fff0b7f5-081f-48c0-a25c-ad796c07d991;Expires=Fri, 12-Dec-2025 02:21:23 GMT), (Content-Length, 0), (Connection, keep-alive)
                Log.d("InfosFragment", "click url")
                val okhttper = OkHttpClient().newBuilder()
                    .addInterceptor { chain->
                        val response = chain.proceed(chain.request())
                        var location = response.headers("Location")
                        if (location == null){
                            location = response.headers("location")
                        }

                        Log.d("InfosFragment", "interceptor: ${response.request.url} ${location} ${response.code} ${response.headers.joinToString()}")
                        return@addInterceptor  response
                    }
                    .followRedirects(false)
                    .build()
                val request = Request.Builder()
                    .head()
//                    .method()
//                    .url("https://www.chengzijianzhan.com/tetris/page/7369427047796293659")
//                    .url("https://lnk0.com/l8g8Yh?td_subid={td_subid}")
                    .url("https://ugapk.com/eT8YF")
                    .build()
                okhttper.newCall(request).enqueue(object :Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("InfosFragment", "onFailure ${e}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("InfosFragment", "${response.request.url} ${response.code} ${response.headers.joinToString()}")
                    }

                })

//                wb.loadUrl("https://lnk0.com/l8g8Yh?td_subid={td_subid}")
                wb.loadUrl("https://www.chengzijianzhan.com/tetris/page/7369427047796293659")
            }
//            Observable.interval(1, TimeUnit.SECONDS)
//                .subscribe {
//                }
            infoViewModel.infoFlow.collect {
                binding.tvNotification.text = "您有一条新消息，请注意查收 ${it}"
            }
            CPay.wxpay(requireActivity(),
                WXPayInfo(null, null, null, null, null, null, null),
                object : IPayCallback {
                    override fun success() {
                        binding.tvNotification.text = "支付成功"
                    }

                    override fun failed(code: Int, message: String?) {
                        binding.tvNotification.text = "支付失败"
                    }

                    override fun cancel() {
                        binding.tvNotification.text = "支付取消"
                    }

                })
        }
    }
}
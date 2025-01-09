package com.electrolytej.main.page.web

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SpanUtils
import com.electrolytej.main.databinding.ActivityWebSearchBinding
import com.electrolytej.main.util.span.RoundedBackgroundSpan

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Jun/01/2024  Sat
 */
class WebSearchActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "WebSearchActivity"
    }
    val binding: ActivityWebSearchBinding by lazy {
        return@lazy ActivityWebSearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.vAd.setOnClickListener {
            Log.d(TAG,"setOnClickListener")
        }
        binding.rvSearchList.layoutManager = layoutManager
        SpanUtils.with(binding.tvAd)
            .append("广告")
            .setFontSize(11, true)
            .setSpans(
                RoundedBackgroundSpan(
                    Color.parseColor(
                        "#FF71A0"
                    ), Color.WHITE, ConvertUtils.dp2px(4f)
                )
            )
            .append("  28元捡漏两套结晶月子服！原价488米错过直拍大腿！入口给你快进群捡漏！")
            .setFontSize(16, true)
            .create()
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //点击搜索按钮会触发
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //搜索框的文本内容发生变化时会触发
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
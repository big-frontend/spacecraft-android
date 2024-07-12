package com.electrolytej.main.page.web

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.electrolytej.main.databinding.ActivityWebSearchBinding

/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Jun/01/2024  Sat
 */
class WebSearchActivity : AppCompatActivity() {
    val binding: ActivityWebSearchBinding by lazy {
        return@lazy ActivityWebSearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvSearchList.layoutManager = layoutManager
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
package com.jamesfchen.bundle2.page.customview.recyclerview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivityRecyclerviewBinding
import com.jamesfchen.bundle2.page.customview.Adapter
import com.jamesfchen.bundle2.page.customview.ViewModel
import jamesfchen.widget.InsetDecoration
import java.util.ArrayList

class RecyclerViewActivity : Activity() {
    var dataList: ArrayList<ViewModel> = object : ArrayList<ViewModel>() {
        init {
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "图片"
                )
            )
            add(
                ViewModel(
                    R.drawable.baseline_3d_rotation_black_48,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    R.drawable.baseline_3d_rotation_black_48,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    R.drawable.baseline_3d_rotation_black_48,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    R.drawable.baseline_3d_rotation_black_48,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    R.drawable.baseline_3d_rotation_black_48,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
            add(
                ViewModel(
                    com.electrolytej.base.R.drawable.tmp,
                    "你好吗我很好，她不好"
                )
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = Adapter()
        binding.rvImageText.adapter = adapter
//        val myLayoutManager = MyLayoutManager()
//        myLayoutManager.startSmoothScroll(MySmoothScroller())
//        rv_image_text.layoutManager =myLayoutManager
        val fglm = FixedGridLayoutManager()
        fglm.setTotalColumnCount(3)
        binding.rvImageText.layoutManager = fglm
        binding.rvImageText.itemAnimator = MyItemAnimator()
        val llm = LinearLayoutManager(this)
        binding.rvImageText.addItemDecoration(InsetDecoration(this))
        binding.rvImageText.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("cjf","scroll state idle")
                }
            }

        })
        binding.rvImageText.setOnFlingListener(object :RecyclerView.OnFlingListener(){
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                Log.d("cjf","onFling")
                return true
            }
        })
        val mySnapHelper = MySnapHelper()
//        mySnapHelper.attachToRecyclerView(rv_image_text)
//        PagerSnapHelper().attachToRecyclerView(rv_image_text)
//        LinearSnapHelper().attachToRecyclerView(rv_image_text)
        adapter.addDatas(dataList)
        binding.btAddView.setOnClickListener {
            val b = object:Button(this){
                override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    Log.d("cjf","onMeasure")
                }
                override fun onAttachedToWindow() {
                    super.onAttachedToWindow()
                    Log.d("cjf","onAttachedToWindow")
                }
            }
            b.text= "btn 3=4"
            binding.llContainer.addView(b, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val b = object:Button(this){
            override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                Log.d("cjf","onMeasure")
            }
            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                Log.d("cjf","onAttachedToWindow")
            }
        }
        b.text= "btn 3"
        binding.llContainer.addView(b, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
package com.jamesfchen.uicomponent.widget.recyclerview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jamesfchen.uicomponent.Adapter
import com.jamesfchen.uicomponent.InsetDecoration
import com.jamesfchen.uicomponent.R
import com.jamesfchen.uicomponent.ViewModel
import kotlinx.android.synthetic.main.activity_recyclerview.*
import java.util.ArrayList

class RecyclerViewActivity : Activity() {
    var dataList: ArrayList<ViewModel> = object : ArrayList<ViewModel>() {
        init {
            add(ViewModel(R.drawable.tmp, "图片"))
            add(ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
            add(ViewModel(R.drawable.tmp, "你好吗我很好，她不好"))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        val adapter = Adapter()
        rv_image_text.adapter = adapter
//        val myLayoutManager = MyLayoutManager()
//        myLayoutManager.startSmoothScroll(MySmoothScroller())
//        rv_image_text.layoutManager =myLayoutManager
        rv_image_text.itemAnimator = MyItemAnimator()
        rv_image_text.addItemDecoration(InsetDecoration(this))
        rv_image_text.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("cjf","scroll state idle")
                }
            }

        })
        rv_image_text.setOnFlingListener(object :RecyclerView.OnFlingListener(){
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
    }
}
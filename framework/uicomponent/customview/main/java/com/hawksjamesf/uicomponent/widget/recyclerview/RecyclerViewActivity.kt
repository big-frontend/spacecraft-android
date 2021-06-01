package com.hawksjamesf.uicomponent.widget.recyclerview

import android.app.Activity
import android.os.Bundle
import com.hawksjamesf.uicomponent.Adapter
import com.hawksjamesf.uicomponent.R
import com.hawksjamesf.uicomponent.ViewModel
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
        val myLayoutManager = MyLayoutManager()
        myLayoutManager.startSmoothScroll(MySmoothScroller())
        rv_image_text.layoutManager =myLayoutManager
        rv_image_text.itemAnimator = MyItemAnimator()
        val mySnapHelper = MySnapHelper()
        mySnapHelper.attachToRecyclerView(rv_image_text)
        adapter.addDatas(dataList)
    }
}
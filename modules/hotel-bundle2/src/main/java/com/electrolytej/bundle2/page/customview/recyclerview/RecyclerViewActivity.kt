package com.electrolytej.bundle2.page.customview.recyclerview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivityRecyclerviewBinding
import com.electrolytej.widget.recyclerview.ArrayAdapter
import com.electrolytej.widget.recyclerview.addDividerItemDecoration
import java.util.Random

class RecyclerViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter(
            R.layout.item_image_and_text,
            intArrayOf(R.id.tv_text, R.id.iv),
            arrayOf(
                "图片", com.electrolytej.base.R.drawable.tmp,
                "图片", com.electrolytej.base.R.drawable.tmp,
                "图片", com.electrolytej.base.R.drawable.tmp
            )
        )
        binding.rvImageText.adapter = adapter
//        val myLayoutManager = MyLayoutManager()
//        myLayoutManager.startSmoothScroll(MySmoothScroller())
//        rv_image_text.layoutManager =myLayoutManager
        val llm = LinearLayoutManager(this)
        val glm = GridLayoutManager(this, 2)
        binding.rvImageText.layoutManager = llm
        binding.rvImageText.itemAnimator = MyItemAnimator()
//        binding.rvImageText.addItemDecoration(InsetDecoration(this))
        binding.rvImageText.addDividerItemDecoration()
        binding.rvImageText.addDividerItemDecoration(orientation = DividerItemDecoration.HORIZONTAL)
        binding.rvImageText.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("cjf", "scroll state idle")
                }
            }

        })
        binding.rvImageText.setOnFlingListener(object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                Log.d("cjf", "onFling")
                return true
            }
        })
        val random = Random()
        binding.btSwitch.setOnClickListener {
//            val fglm = FixedGridLayoutManager()
//            fglm.setTotalColumnCount(3)
            val num = random.nextInt(3)
            if (num == 0) {
                binding.rvImageText.layoutManager = llm
            } else if (num == 1) {
                binding.rvImageText.layoutManager = glm
                adapter.notifyDataSetChanged()
            } else if (num == 2) {

            }

        }
        val mySnapHelper = MySnapHelper()
//        mySnapHelper.attachToRecyclerView(rv_image_text)
//        PagerSnapHelper().attachToRecyclerView(rv_image_text)
//        LinearSnapHelper().attachToRecyclerView(rv_image_text)
//        ArrayAdapter
        binding.btAddView.setOnClickListener {
            val b = object : Button(this) {
                override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    Log.d("cjf", "onMeasure")
                }

                override fun onAttachedToWindow() {
                    super.onAttachedToWindow()
                    Log.d("cjf", "onAttachedToWindow")
                }
            }
            b.text = "btn 3=4"
            binding.flContainer.addView(
                b,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val b = object : Button(this) {
            override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                Log.d("cjf", "onMeasure")
            }

            override fun onAttachedToWindow() {
                super.onAttachedToWindow()
                Log.d("cjf", "onAttachedToWindow")
            }
        }
        b.text = "btn 3"
        binding.flContainer.addView(
            b,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
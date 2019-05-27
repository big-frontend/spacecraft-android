package com.hawksjamesf.spacecraft

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
class CoordinatorLayoutActivity : Activity() {
    lateinit var linearLayoutManager: LinearLayoutManager
    var dataList: MutableList<Int> = object : ArrayList<Int>() {
        init {
            for (i in 0..30) {
                add(i)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinatorlayout)

//        val mRvContent = findViewById<RecyclerView>(R.id.rv_content)
//        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        mRvContent.layoutManager = linearLayoutManager
//        mRvContent.adapter = mContentAdapter
//        val pagerSnapHelper = PagerSnapHelper()
//        pagerSnapHelper.attachToRecyclerView(mRvContent)
//
//        bt.setOnClickListener {
//            linearLayoutManager.orientation = RecyclerView.VERTICAL
//        }


    }


}
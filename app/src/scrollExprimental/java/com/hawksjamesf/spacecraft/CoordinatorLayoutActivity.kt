package com.hawksjamesf.spacecraft

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_coordinatorlayout.*

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
class CoordinatorLayoutActivity : Activity() {
    val mContentAdapter = OuterAdapter()
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

        val mRvContent = findViewById<RecyclerView>(R.id.rv_content)
        mRvContent.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mRvContent.adapter = mContentAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(mRvContent)

        vp_content.adapter = ProxyAdapter()


//        (tl_tabs.layoutParams as CoordinatorLayout.LayoutParams).behavior = TabsBehavior()
    }

    inner class ProxyAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int = mContentAdapter.itemCount
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val viewHolder = mContentAdapter.onCreateViewHolder(container, position)
            mContentAdapter.onBindViewHolder(viewHolder, position)
            container.addView(viewHolder.itemView)
            return viewHolder.itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }
    }

}
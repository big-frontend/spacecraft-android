package com.electrolytej.bundle2.page.customview.newLayoutExprimental.coordinator

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.electrolytej.widget.recyclerview.addDividerItemDecoration

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/22/2019  Fri
 */
class ViewPagerAdapterv2 : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rvPhotoList = RecyclerView(container.context)
        rvPhotoList.setBackgroundColor(Color.CYAN)
        rvPhotoList.layoutManager = LinearLayoutManager(container.context)
        rvPhotoList.addDividerItemDecoration()
        val adapter = RecyclerViewAdapter()
        rvPhotoList.adapter = adapter
        container.addView(rvPhotoList)
        return rvPhotoList
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getCount() = 8

    override fun getPageTitle(position: Int): CharSequence? {
        return "page tilte $position"
    }

}
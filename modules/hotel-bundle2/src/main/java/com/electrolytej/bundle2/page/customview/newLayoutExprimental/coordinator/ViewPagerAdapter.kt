package com.electrolytej.bundle2.page.customview.newLayoutExprimental.coordinator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/22/2019  Fri
 */

class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return ViewPagerFragment.newInstance(position + 1)
    }

    override fun getCount() = 6
    override fun getPageTitle(position: Int): CharSequence? {
        return "page tilte $position"
    }
}
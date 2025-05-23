package com.electrolytej.bundle2.page.customview.newLayoutExprimental.coordinator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.widget.recyclerview.addDividerItemDecoration

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/22/2019  Fri
 */
class ViewPagerFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(sectionNumber: Int): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (context == null) return null
        val rvPhotoList = RecyclerView(requireContext())
        rvPhotoList.setBackgroundColor(Color.CYAN)
        rvPhotoList.layoutManager = LinearLayoutManager(requireContext())
        rvPhotoList.addDividerItemDecoration()
        val adapter = RecyclerViewAdapter()
        rvPhotoList.adapter = adapter
        return rvPhotoList
    }


}

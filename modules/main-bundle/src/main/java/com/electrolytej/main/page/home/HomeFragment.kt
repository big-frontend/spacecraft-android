package com.electrolytej.main.page.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.electrolytej.main.databinding.FragmentHomeBinding
import com.electrolytej.util.dp
import com.electrolytej.widget.viewpager2.transformer.StackPageTransformer
import com.electrolytej.widget.viewpager2.transformer.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = HomeAdapter(this)
        binding.pager.offscreenPageLimit = 3
        binding.pager.isUserInputEnabled = false
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

    class HomeAdapter(f: Fragment) : FragmentStateAdapter(f) {
        val list = listOf(
            BrowserFragment::class.java,
            DoubleListFragment::class.java,
        )

        override fun getItemCount() = list.size

        override fun createFragment(position: Int): Fragment {
            val newInstance = list[position].getDeclaredConstructor().newInstance()
            return newInstance
        }

//        override fun onBindViewHolder(
//            holder: FragmentViewHolder,
//            position: Int,
//            payloads: List<Any?>
//        ) {
//            super.onBindViewHolder(holder, position, payloads)
//            val endInterval = 40.dp
//            val verticalInterval = 40.dp
//            val marginLp = ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            marginLp.marginEnd = (2 - position) * endInterval
//            marginLp.topMargin = position * verticalInterval
//            marginLp.bottomMargin = position * verticalInterval
//            holder.itemView.layoutParams = marginLp
//        }

    }
}
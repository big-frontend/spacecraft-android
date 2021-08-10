package com.jamesfchen.bundle2.kk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.bundle2.R
import com.jamesfchen.bundle2.databinding.ActivityPagerViewBinding
import jamesfchen.widget.kk.TabsLayout

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Jun/16/2019  Sun
 */
class PagerViewActivity : AppCompatActivity() {
    internal var list: List<Int> = object : ArrayList<Int>() {
        init {
            add(1)
            add(2)
            add(3)
            add(4)
            add(5)
            add(6)
            add(7)
            add(8)
            add(9)
        }
    }
    var pagerviewList: List<PagerViewModel> = object : ArrayList<PagerViewModel>() {
        init {
            val tabItem0 = TabsLayout.TabItem("美食", R.mipmap.ic_launcher)
            val tabItem1 = TabsLayout.TabItem("景点", R.drawable.ic_arrow_back_black_24dp)
            val tabItem2 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem3 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem4 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem5 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem6 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem7 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            val tabItem8 = TabsLayout.TabItem("购物", R.drawable.ic_arrow_back_black_24dp)
            add(PagerViewModel(tabItem0, list))
            add(PagerViewModel(tabItem1, list))
            add(PagerViewModel(tabItem2, list))
            add(PagerViewModel(tabItem3, list))
            add(PagerViewModel(tabItem4, list))
            add(PagerViewModel(tabItem5, list))
            add(PagerViewModel(tabItem6, list))
            add(PagerViewModel(tabItem7, list))
            add(PagerViewModel(tabItem8, list))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPagerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter1 = Adapter1()
        binding.pvHorizontal1.setAdapter(adapter1)
        adapter1.setDataList(pagerviewList)

        val pa = PageAdapter()
        binding.pvHorizontal2.setAdapter(pa)
        pa.setDataList(pagerviewList)
        binding.btRemoveAll.setOnClickListener {
            if (pa.isEmpty) {
                pa.setDataList(pagerviewList)
            } else {
                pa.removeAll()
            }
        }

        val adapter2 = Adapter2()
        binding.pvVertical.setAdapter(adapter2)
        adapter2.setDataList(pagerviewList)
        val adapter22 = Adapter22()
        binding.rvAa.adapter = adapter22
        adapter22.setDataList(pagerviewList.get(0).contents)
    }
}

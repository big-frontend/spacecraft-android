package jamesfchen.widget.carousel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.uicomponent.R
import jamesfchen.widget.carousel.adapter.Adapter1
import jamesfchen.widget.carousel.adapter.Adapter11
import kotlinx.android.synthetic.main.activity_pager_view.*
import java.util.*

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

            val tabItem0 = TabsLayout.TabItem("美食", R.drawable.ic_arrow_back_black_24dp)
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
        setContentView(R.layout.activity_pager_view)

        val adapter1 = Adapter1()
        pv_horizontal.setAdapter(adapter1)
        adapter1.setDataList(pagerviewList)
        val adapter11 = Adapter11()
        pv_vertical.setAdapter(adapter11)
        adapter11.setDataList(pagerviewList)
    }
}
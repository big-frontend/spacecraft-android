package com.electrolytej.bundle2.page.kk

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivityPagerViewBinding
import com.electrolytej.loader.systemfilter.SystemFilter
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
        setContentView(TextView(this))
        val binding = ActivityPagerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            if (SystemFilter.isGrayMode) {
                SystemFilter.clearGrayMode(this@PagerViewActivity)
            } else {
                SystemFilter.applyGrayMode(this@PagerViewActivity)
            }
        }
        binding.image.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("title")
            builder.setIcon(com.electrolytej.base.R.drawable.tmp)
            builder.setMessage("message")
            val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_card, null)
//            builder.setView(view)
            builder.setPositiveButton(
                "添加"
            ) { dialog, which ->
//                startActivity(Intent(Bundle2Activity@this, PagerViewActivity::class.java))
                val p = PopupWindow(this@PagerViewActivity)
                p.setContentView(view)
                p.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                p.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                p.showAsDropDown(binding.image)
            }.setNegativeButton(
                "取消"
            ) { dialog, which ->
//                dialog.dismiss()
                val d = Dialog(this@PagerViewActivity,R.style.MyTheme_Dialog)
                d.setContentView(view)
                d.setOwnerActivity(this@PagerViewActivity)
                d.show()
            }
            val dialog: Dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

        }
        var adapter1h = Adapter1H()
        binding.pvHorizontal1.setAdapter(adapter1h)
        adapter1h.setDataList(pagerviewList)


        var adapter1v = Adapter1V()
        binding.pvVertical.setAdapter(adapter1v)
        adapter1v.setDataList(pagerviewList)

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
    }
}

package com.hawksjamesf.common

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.colorpicker.ColorPickerDialog

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
class CoordinatorLayoutActivity : AppCompatActivity() {
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
//        var value:IntArray =
        val value = intArrayOf(
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW,
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW,
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW,
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW,
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW,
                Color.GRAY,
                Color.BLACK,
                Color.BLUE,
                Color.YELLOW
        )
        val newInstance = ColorPickerDialog.newInstance(R.string.color_picker_default_title, value, Color.BLUE, 4, value.size)
//        newInstance.show(supportFragmentManager, "asdf")
//        newInstance.showPaletteView()
//
//        bt.setOnClickListener {
//            newInstance.show(supportFragmentManager, "asdf")
//        }


    }


}
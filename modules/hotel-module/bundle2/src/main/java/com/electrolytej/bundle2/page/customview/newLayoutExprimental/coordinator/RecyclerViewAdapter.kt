package com.electrolytej.bundle2.page.customview.newLayoutExprimental.coordinator

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.bundle2.R

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/22/2019  Fri
 */
class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val image = ImageView(parent.context)
        image.setImageResource(com.electrolytej.base.R.drawable.tmp)
        image.layoutParams = ViewGroup.LayoutParams(200, 200)
        return object : RecyclerView.ViewHolder(image) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount()= 30

}
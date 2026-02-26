package com.electrolytej.ad.page.gesture

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class LeftShakeAdapter : RecyclerView.Adapter<ViewHolder>() {
    val shakeList = mutableListOf<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val textView1 = TextView(parent.context)
        textView1.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView1.setTextColor(Color.WHITE)
        return object : ViewHolder(textView1) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tv = holder.itemView as TextView
        val p = shakeList[position]
        tv.text = "第${position}次\n$p"
    }

    override fun getItemCount(): Int {
        return shakeList.size
    }

    fun clear() {
        shakeList.clear()
        notifyDataSetChanged()
    }

    fun add(shakeTrace: String): Boolean {
        val add = shakeList.add(shakeTrace)
        notifyItemInserted(itemCount - 1)
        return add

    }
}
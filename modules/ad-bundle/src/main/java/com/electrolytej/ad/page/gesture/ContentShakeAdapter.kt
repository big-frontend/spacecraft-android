package com.electrolytej.ad.page.gesture

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ContentShakeAdapter : RecyclerView.Adapter<ViewHolder>() {
    val shakeList = mutableListOf<String>()
    var sampleThreshold = 1000L

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val textView1 = TextView(parent.context)
        textView1.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
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
        lastMs = 0
        shakeList.clear()
        notifyDataSetChanged()
    }
     var refresh = true
    private var lastMs: Long = 0
    fun add(shakeTrace: String?): Boolean {
        if (!refresh) return false
        if (System.currentTimeMillis() - lastMs < sampleThreshold) {
            return false
        }
        lastMs = System.currentTimeMillis()
        if (shakeTrace.isNullOrEmpty()) return false
        shakeList.add(shakeTrace)
        notifyItemInserted(itemCount - 1)
        return true

    }
}
package com.hawksjamesf.spacecraft

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/22/2019  Sun
 */
class CheeseViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.name)
    var cheese : Cheese? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(cheese : Cheese?) {
        this.cheese = cheese
        nameView.text = cheese?.name
    }
}
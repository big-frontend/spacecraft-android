package com.electrolytej.widget.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class StringArrayAdapter(
    @LayoutRes val layoutId: Int,
    @IdRes val textViewResourceId: Int,
    val items: Array<String>
) : RecyclerView.Adapter<StringArrayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = items[position]
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(textViewResourceId)
    }
}

class ArrayAdapter(
    @LayoutRes val layoutId: Int,
    val viewIds: IntArray,
    val items: Array<Any>
) : RecyclerView.Adapter<ArrayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = items.size / viewIds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewIds.forEachIndexed { index, viewid ->
            //0 2 4 6 8 10 12
            //0 1 2 3 4 5 6
            val item = items[position * viewIds.size + index]
            val view = holder.views[viewid]
            if (view is ImageView) {
                view.setImageResource(item as Int)
            } else if (view is Button) {
                view.text = item.toString()
            } else if (view is TextView) {
                view.text = item.toString()
            }
//            Log.d("cjf","${position * viewIds.size + index} ${item} ${view}")
        }
        holder.itemView.setOnClickListener {
            listener?.invoke(holder, position)
        }
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var views = LinkedHashMap<Int, View>()

        init {
            viewIds.forEach { viewId ->
                val v = itemView.findViewById<View>(viewId)
                views.put(viewId, v)

            }
        }
    }
}



package com.electrolytej.widget.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StringArrayAdapter<T>(
    @LayoutRes val layoutId: Int, @IdRes val textViewResourceId: Int, val items: Array<String>
) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val holder = BaseViewHolder<T>(itemView)
        holder.findViewById<View>(textViewResourceId)
        return holder
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.findViewById<TextView>(textViewResourceId)?.text = items[position]
    }
}

class ArrayAdapter2<T> @JvmOverloads constructor(
    @LayoutRes val layoutId: Int,
    val viewIds: IntArray,
    val items: List<T>,
    diffCallback: DiffUtil.ItemCallback<T>,
    config: AsyncDifferConfig<T>? = null
) : ListAdapter<T, BaseViewHolder<T>>(diffCallback) {
    private val proxy: RecyclerViewAdapterProxy<T> = RecyclerViewAdapterProxy(
        layoutId, viewIds, items
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return proxy.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = proxy.getItemCount()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        proxy.onBindViewHolder(holder, position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        proxy.setOnItemClickListener(listener)
    }

    fun addAll(items: List<T>) {
        proxy.addAll(items)
        notifyDataSetChanged()
    }
}

class ArrayAdapter<T>(
    @LayoutRes private val layoutId: Int,
    private val viewIds: IntArray,
    private val items: List<T> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    private val proxy: RecyclerViewAdapterProxy<T> = RecyclerViewAdapterProxy(
        layoutId, viewIds, items
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return proxy.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = proxy.getItemCount()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        proxy.onBindViewHolder(holder, position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        proxy.setOnItemClickListener(listener)
    }

    fun addAll(items: List<T>) {
        proxy.addAll(items)
        notifyDataSetChanged()
    }
}

internal class RecyclerViewAdapterProxy<T>(
    @LayoutRes val layoutId: Int, private val viewIds: IntArray, private var items: List<T>
) {
    //    private val items = mutableListOf<T>()
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val holder = BaseViewHolder<T>(itemView)
        viewIds.forEach { viewId ->
            holder.findViewById<View>(viewId)
        }
        return holder
    }

    fun getItemCount() = items.size / viewIds.size

    fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        for (index in viewIds.indices) {
            val viewid = viewIds[index]
            //0 2 4 6 8 10 12
            //0 1 2 3 4 5 6
            val item = items[position * viewIds.size + index]
            val view = holder.findViewById<View>(viewid) ?: continue
            if (holder.bindData(view, item, position)) continue
            if (view is ImageView) {
                view.setImageResource(item as Int)
            } else if (view is Button) {
                view.text = item.toString()
            } else if (view is TextView) {
                view.text = item.toString()
            }
        }
        holder.itemView.setOnClickListener {
            listener?.invoke(holder, position)
        }
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun addAll(items: List<T>) {
        this.items = items
    }
}

class BaseViewHolder<T>(val rootView: View) : RecyclerView.ViewHolder(rootView) {
    private var views = LinkedHashMap<Int, View>()

    fun <V : View?> findViewById(@IdRes viewId: Int): V? {
        var view: View? = views[viewId]
        if (view == null) {
            view = rootView.findViewById(viewId)
            views[viewId] = view
        }
        return view as? V
    }

    fun bindData(view: View, data: T?, position: Int) = false
}



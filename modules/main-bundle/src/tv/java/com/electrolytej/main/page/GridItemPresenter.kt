package com.electrolytej.main.page

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.Presenter
import com.electrolytej.main.R

class GridItemPresenter(val activity: Activity) : Presenter() {
    companion object {
        private val TAG = "GridItemPresenter"
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = TextView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.default_background))
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        (viewHolder.view as TextView).text = item as String
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
}
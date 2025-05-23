package com.electrolytej.widget.recyclerview

import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.base.R

typealias OnItemClickListener = (View, Int) -> Unit

fun RecyclerView.addDividerItemDecoration(
    @DrawableRes d: Int = R.drawable.line_divider,
    orientation: Int = DividerItemDecoration.VERTICAL, hideLastLine: Boolean = true
) {
    val dividerItemDecoration = PrettyDividerItemDecoration(this.context, orientation)
    dividerItemDecoration.setDrawable(resources.getDrawable(d))
    dividerItemDecoration.hideLastLine = hideLastLine
    this.addItemDecoration(dividerItemDecoration)
}

fun RecyclerView.clearDividerItemDecoration() {

//    for (i in itemDecorationCount){
//    }

}
package com.electrolytej.main.page.detail

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.electrolytej.main.Movie

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val movie = item as Movie

        viewHolder.title.text = movie.title
        viewHolder.subtitle.text = movie.studio
        viewHolder.body.text = movie.description
    }
}